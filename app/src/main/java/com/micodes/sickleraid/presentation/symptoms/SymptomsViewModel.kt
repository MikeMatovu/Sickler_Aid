package com.micodes.sickleraid.presentation.symptoms

import androidx.lifecycle.ViewModel
import com.micodes.sickleraid.domain.services.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SymptomsViewModel @Inject constructor(
    private val accountService: AccountService
) : ViewModel() {

}