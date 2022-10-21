package `is`.ulstu.cardioanalyst.models.diseases

class DiseasesRAMRepository : IDiseasesRepository {
    private var currentDiseases: MutableMap<String, Boolean> = mutableMapOf(
        "Наличие сердечно сосудистых заболеваний у ближайших родственников в возрасте до 70 лет" to false,
        "Прием статинов" to false,
        "Хроническая болезнь почек" to true,
        "Артериальная гипертония" to false,
        "Ишемическая болезнь сердца" to true,
        "Сахарный диабет 2 типа" to false,
        "Инфаркт миокарда" to false,
        "Инсульт" to false,
        "Атеросклероз" to false,
        "Другие заболевания сердечно-сосудистой системы" to false,
    )

    override fun getUserDiseases(token: String): MutableMap<String, Boolean> = currentDiseases

    override fun setUserDiseases(token: String, diseasesMap: Map<String, Boolean>) {
        currentDiseases = diseasesMap.toMutableMap()
    }
}