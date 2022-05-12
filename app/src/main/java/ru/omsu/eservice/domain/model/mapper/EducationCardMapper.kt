package ru.omsu.eservice.domain.model.mapper

import dagger.Reusable
import ru.omsu.eservice.data.remote.login.entity.EducationGroupNetwork
import ru.omsu.eservice.domain.model.EducationGroupUi
import javax.inject.Inject

@Reusable
class EducationCardMapper @Inject constructor() {
    fun map(data: EducationGroupNetwork) : EducationGroupUi =
        EducationGroupUi(
            data.planUrl,
            data.currentControlUrl,
            data.scheduleUrl
        )
}