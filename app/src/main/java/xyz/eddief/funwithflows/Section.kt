package xyz.eddief.funwithflows

import xyz.eddief.funwithflows.viewmodel.CombineTransformViewModel
import xyz.eddief.funwithflows.viewmodel.CombineViewModel
import xyz.eddief.funwithflows.viewmodel.DisplayViewModel2
import xyz.eddief.funwithflows.viewmodel.FilterViewModel
import xyz.eddief.funwithflows.viewmodel.FlatMapConcatViewModel
import xyz.eddief.funwithflows.viewmodel.FlatMapLatestViewModel
import xyz.eddief.funwithflows.viewmodel.FlatMapMergeViewModel
import xyz.eddief.funwithflows.viewmodel.FlowOnViewModel
import xyz.eddief.funwithflows.viewmodel.MergeViewModel
import xyz.eddief.funwithflows.viewmodel.RunningFoldViewModel
import xyz.eddief.funwithflows.viewmodel.RunningReduceViewModel
import xyz.eddief.funwithflows.viewmodel.TimeOutViewModel
import xyz.eddief.funwithflows.viewmodel.ZipViewModel
import kotlin.reflect.KClass

sealed class DisplaySection(val route: String, val vm: Class<out DisplayViewModel2>) {
    object Combine : DisplaySection("combine", CombineViewModel::class.java)
    object Merge : DisplaySection("merge", MergeViewModel::class.java)
    object Zip : DisplaySection("zip", ZipViewModel::class.java)
    object Filter : DisplaySection("filter", FilterViewModel::class.java)
    object FlatMapLatest : DisplaySection("flatMapLatest", FlatMapLatestViewModel::class.java)
    object FlatMapConcat : DisplaySection("flatMapConcat", FlatMapConcatViewModel::class.java)
    object FlatMapMerge : DisplaySection("flatMapMerge", FlatMapMergeViewModel::class.java)
    object FlowOn : DisplaySection("flowOn", FlowOnViewModel::class.java)
    object CombineTransform :
        DisplaySection("combineTransform", CombineTransformViewModel::class.java)
    object RunningFold :
        DisplaySection("runningFold", RunningFoldViewModel::class.java)
    object RunningReduce :
        DisplaySection("runningReduce", RunningReduceViewModel::class.java)

    object TimeOut : DisplaySection("timeout / retry", TimeOutViewModel::class.java)


    companion object {
        val sections = DisplaySection::class.sealedSubclasses
            .mapNotNull(KClass<out DisplaySection>::objectInstance)
    }
}