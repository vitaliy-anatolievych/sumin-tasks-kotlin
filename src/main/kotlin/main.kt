data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val genre: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Book) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

data class User(
    val id: Int,
    val name: String,
    val borrowedBooks: MutableList<Book> = mutableListOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

class Library {
    private val books = mutableListOf<Book>()
    private val users = mutableListOf<User>()

    fun addBook(book: Book): Boolean {
        if (books.contains(book)) {
            println("Книга з ID ${book.id} вже існує у бібліотеці.")
            return false
        }
        books.add(book)
        println("Книга '${book.title}' додана до бібліотеки.")
        return true
    }

    fun registerUser(user: User): Boolean {
        if (users.contains(user)) {
            println("Користувач з ID ${user.id} вже зареєстрований.")
            return false
        }
        users.add(user)
        println("Користувач '${user.name}' зареєстрований.")
        return true
    }

    fun borrowBook(userId: Int, bookId: Int): Boolean {
        val user = users.find { it.id == userId }
        val book = books.find { it.id == bookId }

        if (user == null) {
            println("Користувач з ID $userId не знайдений.")
            return false
        }

        if (book == null) {
            println("Книга з ID $bookId не знайдена.")
            return false
        }

        // Перевіряємо, чи книга вже взята іншим користувачем
        if (users.any { it.borrowedBooks.contains(book) }) {
            println("Книга '${book.title}' вже взята іншим користувачем.")
            return false
        }

        if (user.borrowedBooks.size >= 5) {
            println("Користувач '${user.name}' не може взяти більше 5 книг.")
            return false
        }

        if (user.borrowedBooks.contains(book)) {
            println("Користувач '${user.name}' вже взяв цю книгу.")
            return false
        }

        user.borrowedBooks.add(book)
        books.remove(book)
        println("Користувач '${user.name}' взяв книгу '${book.title}'.")
        return true
    }

    fun returnBook(userId: Int, bookId: Int): Boolean {
        val user = users.find { it.id == userId }
        val book = books.find { it.id == bookId }

        if (user == null) {
            println("Користувач з ID $userId не знайдений.")
            return false
        }

        if (book == null) {
            println("Книга з ID $bookId не знайдена.")
            return false
        }

        if (!user.borrowedBooks.contains(book)) {
            println("Користувач '${user.name}' не має книги '${book.title}' для повернення.")
            return false
        }

        user.borrowedBooks.remove(book)
        books.add(book)
        println("Користувач '${user.name}' повернув книгу '${book.title}'.")
        return true
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

fun main() {
    val library = Library()

    // Додавання книг
    library.addBook(Book(1, "1984", "George Orwell", "Dystopian"))
    library.addBook(Book(2, "Brave New World", "Aldous Huxley", "Dystopian"))
    library.addBook(Book(1, "Duplicate", "Author", "Genre")) // Не додається

    // Реєстрація користувачів
    library.registerUser(User(1, "Alice"))
    library.registerUser(User(2, "Bob"))

    // Взяття книг
    library.borrowBook(1, 1) // Alice бере книгу з ID 1
    library.borrowBook(2, 2) // Bob бере книгу з ID 2
    library.borrowBook(1, 2) // Alice бере книгу з ID 2

    // Повернення книг
    library.returnBook(1, 1) // Alice повертає книгу з ID 1
    library.returnBook(2, 2) // Bob повертаэ книгу з ID 2

    // Перевірка стану бібліотеки
    println(library)
}
