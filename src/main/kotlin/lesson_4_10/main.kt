package lesson_4_10

import lesson_4_10.entity.Customer
import lesson_4_10.entity.DiscountedOrder
import lesson_4_10.entity.Order
import lesson_4_10.entity.Product

fun main() {
    println("Запуск тестів для DiscountedOrder...")
    testTotalPriceWithDiscount()
    testZeroDiscount()
    testFullDiscount()
    testInvalidDiscount()
    println("Усі тести пройдено успішно!")

    // Створення продуктів
    val product1 = Product(1, "Ноутбук", 1500.0, "Електроніка")
    val product2 = Product(2, "Мишка", 25.0, "Електроніка")

    // Реєстрація клієнта
    val customer = Customer(1, "Олексій", "olexiy@gmail.com")

    // Створення звичайного замовлення
    val order = Order(1, customer, listOf(product1, product2))
    println("Звичайне замовлення: $order")

    // Створення замовлення зі знижкою 10%
    val discountedOrder = DiscountedOrder(2, customer, listOf(product1, product2), 10.0)
    println("Замовлення зі знижкою: $discountedOrder")
}

fun testTotalPriceWithDiscount() {
    // GIVEN
    val customer = Customer(1, "Олексій", "olexiy@gmail.com")
    val product1 = Product(1, "Ноутбук", 1500.0, "Електроніка")
    val product2 = Product(2, "Мишка", 25.0, "Електроніка")

    // WHEN
    val discountedOrder = DiscountedOrder(1, customer, listOf(product1, product2), 10.0)

    // THEN
    val expectedTotal = 1372.5 // 1500 + 25 - 10%
    val actualTotal = discountedOrder.totalPrice
    assert(expectedTotal == actualTotal) {
        "Тест провалено: Очікувана сума $expectedTotal, отримана $actualTotal"
    }

    println("Тест testTotalPriceWithDiscount пройдено.")
}

fun testZeroDiscount() {
    // GIVEN
    val customer = Customer(1, "Олексій", "olexiy@gmail.com")
    val product1 = Product(1, "Ноутбук", 1500.0, "Електроніка")

    // WHEN
    val discountedOrder = DiscountedOrder(1, customer, listOf(product1), 0.0)

    // THEN
    val expectedTotal = 1500.0 // Без знижки
    val actualTotal = discountedOrder.totalPrice
    assert(expectedTotal == actualTotal) {
        "Тест провалено: Очікувана сума $expectedTotal, отримана $actualTotal"
    }

    println("Тест testZeroDiscount пройдено.")
}

fun testFullDiscount() {
    // GIVEN
    val customer = Customer(1, "Олексій", "olexiy@gmail.com")
    val product1 = Product(1, "Ноутбук", 1500.0, "Електроніка")

    // WHEN
    val discountedOrder = DiscountedOrder(1, customer, listOf(product1), 100.0)

    // THEN
    val expectedTotal = 0.0 // Повна знижка
    val actualTotal = discountedOrder.totalPrice
    assert(expectedTotal == actualTotal) {
        "Тест провалено: Очікувана сума $expectedTotal, отримана $actualTotal"
    }

    println("Тест testFullDiscount пройдено.")
}

fun testInvalidDiscount() {
    // GIVEN
    val customer = Customer(1, "Олексій", "olexiy@gmail.com")
    val product1 = Product(1, "Ноутбук", 1500.0, "Електроніка")

    // THEN
    try {
        val discountedOrder = DiscountedOrder(1, customer, listOf(product1), -10.0)
        throw AssertionError("Тест провалено: Знижка не може бути від'ємною")
    } catch (e: IllegalArgumentException) {
        assert(e.message == "Знижка повинна бути в межах від 0 до 100%") {
            "Тест провалено: Очікуване повідомлення про помилку не співпадає"
        }
    }

    try {
        val discountedOrder = DiscountedOrder(1, customer, listOf(product1), 110.0)
        throw AssertionError("Тест провалено: Знижка не може бути більше 100%")
    } catch (e: IllegalArgumentException) {
        assert(e.message == "Знижка повинна бути в межах від 0 до 100%") {
            "Тест провалено: Очікуване повідомлення про помилку не співпадає"
        }
    }

    println("Тест testInvalidDiscount пройдено.")
}