package lesson_4_8.entity

/**
 * Представляє користувача бібліотеки
 *
 * @property id Унікальний ідентифікатор користувача
 * @property name Ім'я користувача
 * @property borrowedBooks Список взятих книг
 * @throws IllegalArgumentException якщо id від'ємний або ім'я пусте
 */
data class User(
    val id: Int,
    val name: String,
    private val _borrowedBooks: MutableList<Book> = mutableListOf(),
    private val maxBooks: Int = 5
) {
    init {
        require(id >= 0) { "ID користувача не може бути від'ємним" }
        require(name.isNotBlank()) { "Ім'я користувача не може бути пустим" }
        require(maxBooks > 0) { "Максимальна кількість книг має бути більше 0" }
    }

    val borrowedBooks: List<Book>
        get() = _borrowedBooks.toList()

    internal fun addBook(book: Book): Boolean {
        if (_borrowedBooks.size >= maxBooks) {
            return false
        }
        if (_borrowedBooks.contains(book)) {
            return false
        }
        return _borrowedBooks.add(book)
    }

    internal fun removeBook(book: Book) {
        _borrowedBooks.remove(book)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}