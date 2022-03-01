package gecko.ui.composition

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import gecko.android.GeckoSource
import gecko.android.GeckoSourceFactory
import gecko.ui.presentation.Worker
import gecko.ui.screen.detail.DetailContent
import gecko.ui.screen.detail.DetailContentLoader
import gecko.ui.screen.detail.DetailViewModel

@Composable
fun DetailView(entry: NavBackStackEntry) {
    val id = entry.arguments?.getString("id")
        ?: throw IllegalStateException("Detail requires an \"id\" argument")
    val viewModel: DetailViewModel = viewModel(key = id, factory = DetailViewModelFactory(id))
    val context = LocalContext.current
    DetailContent(viewModel)
    Worker(
        viewModel,
        remember(viewModel, context) { DetailContentLoader(getSource(context)) }
    )
}

private fun getSource(context: Context): GeckoSource {
    return GeckoSourceFactory.getInstance(context).getInstance()
}

private class DetailViewModelFactory(
    private val id: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailViewModel(id) as T
    }
}