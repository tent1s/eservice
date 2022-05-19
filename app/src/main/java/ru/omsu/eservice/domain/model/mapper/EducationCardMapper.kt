package ru.omsu.eservice.domain.model.mapper

import dagger.Reusable
import ru.omsu.eservice.data.remote.login.entity.EducationGroupNetwork
import ru.omsu.eservice.domain.model.*
import javax.inject.Inject

@Reusable
class EducationCardMapper @Inject constructor() {
    fun map(data: EducationGroupNetwork) : EducationGroupUi =
        EducationGroupUi(
            data.planUrl,
            data.currentControlUrl,
            data.scheduleUrl,
            SumInfo(
                data.summary?.planId,
                data.summary?.group,
                data.summary?.ins,
                data.summary?.fullName,
                data.summary?.birthDay,
                data.summary?.faculty,
                data.summary?.specification,
                data.summary?.studyForm,
                data.summary?.financing,
                data.summary?.studyLength,
                data.summary?.course,
                data.summary?.dupId,
                data.summary?.dupIdExpress,
                data.summary?.week
            ),
            data.documents?.map {
                Documents(
                    it.type,
                    it.series,
                    it.number,
                    it.issuedBy
                )
            } ?: run { listOf() },
            data.sessions?.map {
                Sessions(
                    it.number,
                    it.entries?.map { entries ->
                        Entries(
                            entries.subject,
                            entries.result
                        )
                    } ?: run { listOf() }
                )
            } ?: run { listOf() },
            data.addresses?.map {
                Addresses(
                    it.fullPath,
                    it.dateBegin,
                    it.type
                )
            } ?: run { listOf() },
            data.decrees?.map {
                Decrees(
                    it.id,
                    it.date,
                    it.number,
                    it.about
                )
            } ?: run { listOf() },
            data.semInfo?.map {
                SemInfo(
                    it.number,
                    it.entries?.map { item ->
                        EntriesSeminar(
                            item.discipline,
                            item.discId,
                            item.planId,
                            item.length,
                            item.rpd,
                            item.apx,
                            item.controlForm
                        )
                    } ?: run { listOf() }
                )
            } ?: run { listOf() }

        )
}