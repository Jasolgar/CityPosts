package es.jasolgar.posts.ui.posts;

import androidx.recyclerview.widget.LinearLayoutManager;

import dagger.Module;
import dagger.Provides;
import es.jasolgar.posts.ui.base.BaseActivity;
import es.jasolgar.posts.ui.main.MainActivity;

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
