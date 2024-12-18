package lesson_4_10.entity

open class Product(
    val id: Int,
    val name: String,
    open val price: Double, // Зроблено open
    val category: String
) {
    init {
        require(id > 0) { "ID продукту має бути додатнім" }
        require(name.isNotBlank()) { "Назва продукту не може бути порожньою" }
        require(price >= 0) { "Ціна продукту не може бути від'ємною" }
    }

    override fun toString(): String {
        return "Продукт: $name (ID: $id, Ціна: $price, Категорія: $category)"
    }
}