package lesson_4_8

import lesson_4_8.entity.Book
import lesson_4_8.entity.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class MainKtTest {
    private lateinit var library: Library

    @BeforeEach
    fun setup() {
        library = Library()
    }

    @Test
    fun `WHEN User is registered THEN return true`() {
        // Given
        val user = User(0, "Alice")

        // When
        val result = library.registerUser(user)

        // Then
        assertTrue(result)
    }

    @Test
    fun `WHEN Users is registered AND id is valid THEN return true`() {
        // Given
        val userAlice = User(0, "Alice")
        val userBob = User(1, "Bob")

        // When
        val resultUserAlice = library.registerUser(userAlice)
        val resultUserBob = library.registerUser(userBob)

        // Then
        assertTrue(resultUserAlice)
        assertTrue(resultUserBob)
    }

    @Test
    fun `WHEN User with duplicate ID is registered THEN return false for second user`() {
        // Given
        val userAlice = User(0, "Alice")
        val userBob = User(0, "Bob")

        // When
        val resultUserAlice = library.registerUser(userAlice)
        val resultUserBob = library.registerUser(userBob)

        // Then
        assertTrue(resultUserAlice)
        assertFalse(resultUserBob)
    }

    @Test
    fun `WHEN book is added to library THEN return true`() {
        // Given
        val book = Book(0, "1984", "George Orwell", "Dystopian")

        // When
        val result = library.addBook(book)

        // Then
        assertTrue(result)
    }

    @Test
    fun `WHEN add to library book with duplicate ID THEN return false for second book`() {
        // Given
        val book1 = Book(0, "1984", "George Orwell", "Dystopian")
        val book2 = Book(0, "1984", "George Orwell", "Dystopian")

        // When
        val result1 = library.addBook(book1)
        val result2 = library.addBook(book2)

        // Then
        assertTrue(result1)
        assertFalse(result2)
    }

    @Test
    fun `WHEN User borrows book THEN return true`() {
        // Given
        val book = Book(0, "1984", "George Orwell", "Dystopian")
        val user = User(1, "Alice")

        // When
        library.addBook(book)
        library.registerUser(user)

        val borrowResult = library.borrowBook(user.id, book.id)

        // Then
        assertTrue(borrowResult)
    }

    @Test
    fun `WHEN User tries to borrow a Book with undefined ID THEN return false`() {
        // Given
        val undefinedBookId = 0
        val user = User(1, "Alice")

        // When
        library.registerUser(user)

        val borrowResult = library.borrowBook(user.id, undefinedBookId)

        // Then
        assertFalse(borrowResult)
    }

    @Test
    fun `WHEN User borrows and returns a Book THEN both operations succeed`() {
        // Given
        val book = Book(0, "1984", "George Orwell", "Dystopian")
        val user = User(1, "Alice")

        // When
        library.addBook(book)
        library.registerUser(user)

        val borrowResult = library.borrowBook(user.id, book.id)
        val returnResult = library.returnBook(user.id, book.id)

        // Then
        assertTrue(borrowResult)
        assertTrue(returnResult)
    }

    @Test
    fun `WHEN Book is borrowed by one User THEN second User cannot borrow the same Book`() {
        // Given
        val book = Book(0, "1984", "George Orwell", "Dystopian")
        val userAlice = User(0, "Alice")
        val userBob = User(1, "Bob")

        // When
        library.addBook(book)
        library.registerUser(userAlice)
        library.registerUser(userBob)

        val borrowAliceResult = library.borrowBook(userAlice.id, book.id)
        val borrowBobResult = library.borrowBook(userBob.id, book.id)

        // Then
        assertTrue(borrowAliceResult)
        assertFalse(borrowBobResult)
    }

    @Test
    fun `WHEN User tries to borrow more than 5 books THEN return false`() {
        // Given
        val user = User(1, "Alice")
        library.registerUser(user)

        // When & Then
        repeat(5) { id ->
            val book = Book(id, "Book $id", "Author", "Genre")
            library.addBook(book)
            assertTrue(library.borrowBook(user.id, book.id))
        }

        val sixthBook = Book(6, "Book 6", "Author", "Genre")
        library.addBook(sixthBook)
        assertFalse(library.borrowBook(user.id, sixthBook.id))
    }

    @Test
    fun `WHEN Wrong user tries to return book THEN return false`() {
        // Given
        val book = Book(0, "1984", "George Orwell", "Dystopian")
        val userAlice = User(1, "Alice")
        val userBob = User(2, "Bob")

        // When
        library.addBook(book)
        library.registerUser(userAlice)
        library.registerUser(userBob)
        library.borrowBook(userAlice.id, book.id)

        // Then
        assertFalse(library.returnBook(userBob.id, book.id))
    }

    @Test
    fun `WHEN two Books have the same ID THEN equals returns true`() {
        // Given
        val book1 = Book(1, "1984", "George Orwell", "Dystopian")
        val book2 = Book(1, "Animal Farm", "George Orwell", "Political Satire")

        // When
        val areEqual = book1 == book2

        // Then
        assertTrue(areEqual, "Books with the same ID should be equal")
    }

    @Test
    fun `WHEN two Books have different IDs THEN equals returns false`() {
        // Given
        val book1 = Book(1, "1984", "George Orwell", "Dystopian")
        val book2 = Book(2, "1984", "George Orwell", "Dystopian")

        // When
        val areEqual = book1 == book2

        // Then
        assertFalse(areEqual, "Books with different IDs should not be equal")
    }

    @Test
    fun `WHEN two Books have the same ID THEN hashCode is the same`() {
        // Given
        val book1 = Book(1, "1984", "George Orwell", "Dystopian")
        val book2 = Book(1, "Animal Farm", "George Orwell", "Political Satire")

        // When
        val hashCode1 = book1.hashCode()
        val hashCode2 = book2.hashCode()

        // Then
        assertEquals(hashCode1, hashCode2, "Books with the same ID should have the same hashCode")
    }

    @Test
    fun `WHEN two Books have different IDs THEN hashCode is different`() {
        // Given
        val book1 = Book(1, "1984", "George Orwell", "Dystopian")
        val book2 = Book(2, "1984", "George Orwell", "Dystopian")

        // When
        val hashCode1 = book1.hashCode()
        val hashCode2 = book2.hashCode()

        // Then
        assertNotEquals(hashCode1, hashCode2, "Books with different IDs should have different hashCodes")
    }

    @Test
    fun `WHEN two Users have the same ID THEN equals returns true`() {
        // Given
        val user1 = User(1, "Alice")
        val user2 = User(1, "Bob") // Інше ім'я, але той самий ID

        // When
        val areEqual = user1 == user2

        // Then
        assertTrue(areEqual, "Users with the same ID should be equal")
    }

    @Test
    fun `WHEN two Users have different IDs THEN equals returns false`() {
        // Given
        val user1 = User(1, "Alice")
        val user2 = User(2, "Alice")

        // When
        val areEqual = user1 == user2

        // Then
        assertFalse(areEqual, "Users with different IDs should not be equal")
    }

    @Test
    fun `WHEN two Users have the same ID THEN hashCode is the same`() {
        // Given
        val user1 = User(1, "Alice")
        val user2 = User(1, "Bob") // Інше ім'я, але той самий ID

        // When
        val hashCode1 = user1.hashCode()
        val hashCode2 = user2.hashCode()

        // Then
        assertEquals(hashCode1, hashCode2, "Users with the same ID should have the same hashCode")
    }

    @Test
    fun `WHEN two Users have different IDs THEN hashCode is different`() {
        // Given
        val user1 = User(1, "Alice")
        val user2 = User(2, "Alice")

        // When
        val hashCode1 = user1.hashCode()
        val hashCode2 = user2.hashCode()

        // Then
        assertNotEquals(hashCode1, hashCode2, "Users with different IDs should have different hashCodes")
    }
}