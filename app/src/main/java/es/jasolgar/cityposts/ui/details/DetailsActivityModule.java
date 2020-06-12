package es.jasolgar.cityposts.ui.details;

import androidx.recyclerview.widget.LinearLayoutManager;

import dagger.Module;
import dagger.Provides;

@Module
public class DetailsActivityModule {

    @Provides
    LinearLayoutManager provideLinearLayoutManager(DetailsActivity detailsActivity) {
        return new LinearLayoutManager(detailsActivity);
    }

    @Provides
    CommentsAdapter provideCommentsAdapter(){
        return new CommentsAdapter();
    }

}
