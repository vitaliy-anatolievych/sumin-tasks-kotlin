package lesson_4_10.entity

open class Order(
    val id: Int,
    val customer: Customer,
    val products: List<Product>
) {
    open val totalPrice: Double // Зроблено open
        get() = products.sumOf { it.price }

    init {
        require(id > 0) { "ID замовлення має бути додатнім" }
        require(products.isNotEmpty()) { "Список продуктів не може бути порожнім" }
    }

    override fun toString(): String {
        return "Замовлення: ID: $id, Клієнт: ${customer.name}, Загальна сума: $totalPrice"
    }
}