package lesson_4_10.entity

class DiscountedOrder(
    id: Int,
    customer: Customer,
    products: List<Product>,
    private val discountPercentage: Double
) : Order(id, customer, products) {
    override val totalPrice: Double
        get() = super.totalPrice * (1 - discountPercentage / 100)

    init {
        require(discountPercentage in 0.0..100.0) { "Знижка повинна бути в межах від 0 до 100%" }
    }

    override fun toString(): String {
        return "Замовлення зі знижкою: ID: $id, Клієнт: ${customer.name}, Сума зі знижкою: $totalPrice"
    }
}