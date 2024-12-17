package lesson_4_8.entity

/**
 * Представляє книгу в бібліотечній системі
 *
 * @property id Унікальний ідентифікатор книги
 * @property title Назва книги
 * @property author Автор книги
 * @property genre Жанр книги
 * @throws IllegalArgumentException якщо id від'ємний або поля пусті
 */
data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val genre: String
) {
    init {
        require(id >= 0) { "ID книги не може бути від'ємним" }
        require(title.isNotBlank()) { "Назва книги не може бути пустою" }
        require(author.isNotBlank()) { "Ім'я автора не може бути пустим" }
        require(genre.isNotBlank()) { "Жанр не може бути пустим" }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Book) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}