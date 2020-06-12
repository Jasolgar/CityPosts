package es.jasolgar.cityposts.ui.posts;

import androidx.recyclerview.widget.LinearLayoutManager;

import dagger.Module;
import dagger.Provides;
import es.jasolgar.cityposts.ui.base.BaseActivity;
import es.jasolgar.cityposts.ui.main.MainActivity;

@Module
public class PostsFragmentModule {

    @Provides
    BaseActivity provideBaseActivity(PostsFragment fragment){
        return (BaseActivity) fragment.getActivity();
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(PostsFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }

    @Provides
    PostsAdapter provideOpenSourceAdapter(MainActivity baseActivity) {
        return new PostsAdapter(baseActivity);
    }



}
