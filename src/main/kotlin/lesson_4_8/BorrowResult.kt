package lesson_4_8

import lesson_4_8.entity.Book
import lesson_4_8.entity.User

sealed class BorrowResult {
    data class Success(val message: String) : BorrowResult() {
        override fun toString(): String {
            return "Success: $message"
        }
    }

    sealed class Error(private val e: String) : BorrowResult() {
        class UserNotFound(userId: Int) : Error("Користувач з ID $userId не знайдена.")
        class UserAlreadyExist(user: User): Error("Користувач з ID ${user.id} вже зареєстрований.")
        class BookNotFound(bookId: Int) : Error("Книга з ID $bookId не знайдена.")
        class BookAlreadyExist(book: Book): Error("Книга '${book.title}' вже існує у бібліотеці.")
        class BookAlreadyBorrowed(book: Book) : Error("Книга '${book.title}' вже взята іншим користувачем.")
        class UserLimitExceeded(user: User) : Error("Користувач '${user.name}' не може взяти більше 5 книг.")
        class UserHasNoBook(user: User, book: Book): Error("Користувач '${user.name}' не має книги '${book.title}' для повернення.")

        override fun toString(): String {
            return "Error: $e"
        }
    }
}