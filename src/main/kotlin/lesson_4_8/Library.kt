package lesson_4_8

import lesson_4_8.entity.Book
import lesson_4_8.entity.User

class Library {
    private val books = mutableListOf<Book>()
    private val users = mutableListOf<User>()

    fun addBook(book: Book): Boolean {
        return synchronized(this) {
            val result = when {
                books.contains(book) -> BorrowResult.Error.BookAlreadyExist(book)

                else -> {
                    books.add(book)
                    BorrowResult.Success("Книга '${book.title}' додана до бібліотеки.")
                }
            }

            println(result)
            result is BorrowResult.Success
        }
    }

    fun registerUser(user: User): Boolean {
        return synchronized(this) {
            val result = when {
                users.contains(user) -> BorrowResult.Error.UserAlreadyExist(user)
                else -> {
                    users.add(user)
                    BorrowResult.Success("Користувач '${user.name}' зареєстрований.")
                }
            }

            println(result)
            result is BorrowResult.Success
        }
    }

    fun borrowBook(userId: Int, bookId: Int): Boolean {
        return synchronized(this) {
            val user = users.find { it.id == userId }
            val book = books.find { it.id == bookId }

            val result = when {
                user == null -> BorrowResult.Error.UserNotFound(userId)
                book == null -> BorrowResult.Error.BookNotFound(bookId)
                users.any { it.borrowedBooks.contains(book) } ->
                    BorrowResult.Error.BookAlreadyBorrowed(book)

                user.borrowedBooks.size >= 5 ->
                    BorrowResult.Error.UserLimitExceeded(user)

                else -> {
                    user.addBook(book)
                    books.remove(book)
                    BorrowResult.Success("Користувач '${user.name}' взяв книгу '${book.title}'.")
                }
            }

            println(result)
            result is BorrowResult.Success
        }
    }

    fun returnBook(userId: Int, bookId: Int): Boolean {
        return synchronized(this) {
            val user = users.find { it.id == userId }
            val book = user?.borrowedBooks?.find { it.id == bookId }

            val result = when {
                user == null -> BorrowResult.Error.UserNotFound(userId)
                book == null -> BorrowResult.Error.BookNotFound(bookId)
                !user.borrowedBooks.contains(book) -> BorrowResult.Error.UserHasNoBook(user, book)

                else -> {
                    user.removeBook(book)
                    books.add(book)
                    BorrowResult.Success("Користувач '${user.name}' повернув книгу '${book.title}'.")
                }
            }

            println(result)
            result is BorrowResult.Success
        }
    }

    override fun toString(): String {
        val booksInfo = if (books.isEmpty()) {
            "В бібліотеці відсутні книги"
        } else {
            books.joinToString(separator = "\n") { "Книга: ${it.title} (ID: ${it.id})" }
        }

        val usersInfo = if (users.isEmpty()) {
            "В бібліотеці немає зареєстрованих користувачів"
        } else {
            users.joinToString(separator = "\n") { user ->
                val borrowed = user.borrowedBooks.joinToString { it.title }
                "Користувач: ${user.name} (ID: ${user.id}), Книги: [$borrowed]"
            }
        }

        return "Бібліотека:\n$booksInfo\n\nКористувачі:\n$usersInfo"
    }
}