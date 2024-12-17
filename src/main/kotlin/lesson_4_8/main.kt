package lesson_4_8

import lesson_4_8.entity.Book
import lesson_4_8.entity.User


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
