package fathi.reza.been_tech.Data.CatFragment

data class SecondLevel(
    val icon: String,
    val id: String,
    val parent: String,
    val subarrayinthirdlevel: List<ThirdLevel>,
    val title: String,
    val totalProducts: Int
)