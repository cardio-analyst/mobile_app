package `is`.ulstu.cardioanalyst.models.diseases

class DiseasesDBRepository : IDiseasesRepository {
    override fun getUserDiseases(token: String): MutableMap<String, Boolean> {
        TODO("Not yet implemented")
    }

    override fun setUserDiseases(token: String, diseasesMap: Map<String, Boolean>) {
        TODO("Not yet implemented")
    }
}