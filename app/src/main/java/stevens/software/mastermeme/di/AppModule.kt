package stevens.software.mastermeme.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import stevens.software.mastermeme.memes.MemesRepository
import stevens.software.mastermeme.memes.MyMemesViewModel

val appModule = module {
    factoryOf(::MemesRepository)
    viewModelOf(::MyMemesViewModel)
}