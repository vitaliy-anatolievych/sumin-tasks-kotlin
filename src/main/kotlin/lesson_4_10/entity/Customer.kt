package lesson_4_10.entity


data class Customer(
    val id: Int,
    val name: String,
    val email: String
) {
    init {
        require(id > 0) { "ID клієнта має бути додатнім" }
        require(name.isNotBlank()) { "Ім'я клієнта не може бути порожнім" }
        require(email.contains("@") && email.contains(".")) { "Некоректний формат електронної пошти" }
    }
}
