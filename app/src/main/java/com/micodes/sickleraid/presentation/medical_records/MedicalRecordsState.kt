package com.micodes.sickleraid.presentation.medical_records

import androidx.compose.ui.graphics.painter.Painter

data class MedicalRecordsState (
    val bmi: Int = 0,
    val packetCellVolume: Int = 0,
    val peripheralCapillarity: Int = 0,
    val fetalHaemoglobin: Int = 0,
    val meanCorpuscularVolume: Int = 0,
    val alanineAminotransferase: Int = 0,
    val birulubin: Int = 0,
    val lactateDehydrogenase: Int = 0,
    val platelets: Int = 0,
)