package com.aidanvii.mybingelist.core

import android.databinding.Bindable
import android.support.v7.widget.GridLayoutManager
import com.aidanvii.mybingelist.core.redux.AppAction
import com.aidanvii.mybingelist.core.redux.AppState
import com.aidanvii.toolbox.adapterviews.recyclerview.BindingRecyclerViewBinder
import com.aidanvii.toolbox.databinding.ObservableViewModel
import com.aidanvii.toolbox.databinding.bindable
import com.aidanvii.toolbox.redux.Store
import com.aidanvii.toolbox.rxutils.RxSchedulers
import com.aidanvii.toolbox.rxutils.disposable
import io.reactivex.rxkotlin.subscribeBy
import java.util.concurrent.atomic.AtomicBoolean

abstract class MovieListViewModel(
    protected val store: Store<AppAction, AppState>,
    private val rxSchedulers: RxSchedulers
) : ObservableViewModel() {

    private val initialised = AtomicBoolean()

    @get:Bindable
    var lastKnownState: AppState? by bindable(null)

    private var storeDisposable by disposable()

    val binder = BindingRecyclerViewBinder<MovieAdapterItem>(
        hasMultipleViewTypes = false,
        layoutManagerFactory = { GridLayoutManager(it, 2, GridLayoutManager.VERTICAL, false) }
    )

    @get:Bindable
    var movieAdapterItems: List<MovieAdapterItem> by bindable(emptyList())
        private set

    fun init() {
        if (!initialised.getAndSet(true)) {
            storeDisposable = subscribeToStore()
        }
    }

    private fun subscribeToStore() = store.stateObservable
        .observeOn(rxSchedulers.computation)
        .doOnNext { lastKnownState = it; onNextState(it) }
        .map(this::toMovieAdapterItems)
        .observeOn(rxSchedulers.main)
        .subscribeBy { newMovieAdapterItems ->
            movieAdapterItems = newMovieAdapterItems
        }

    abstract fun toMovieAdapterItems(state: AppState): List<MovieAdapterItem>

    open fun onNextState(state: AppState) {}

    override fun onDisposed() {
        storeDisposable = null
    }
}