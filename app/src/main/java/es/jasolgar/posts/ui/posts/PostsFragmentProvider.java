package es.jasolgar.posts.ui.posts;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class PostsFragmentProvider {
    @ContributesAndroidInjector(modules = PostsFragmentModule.class)
    abstract PostsFragment providePostsFragmentFactory();
}
