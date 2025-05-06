package com.example.lab_6.di

import com.example.lab_6.ui.screens.gallery.GalleryViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { GalleryViewModel(get(), get()) }
}