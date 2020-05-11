package es.jasolgar.posts.ui.details;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import javax.inject.Inject;

import es.jasolgar.posts.R;
import es.jasolgar.posts.ViewModelProviderFactory;
import es.jasolgar.posts.databinding.ActivityDetailsBinding;
import es.jasolgar.posts.ui.base.BaseActivity;
import es.jasolgar.posts.utils.AppLogger;

public class DetailsActivity  extends BaseActivity<ActivityDetailsBinding, DetailsViewModel> implements DetailsNavigator {

    private static final String  BUNDLE_POST_ID = "bundle_post_id";
    private static final int REQUEST_PERMISSION_PHONE_CALL = 0;

    @Inject
    CommentsAdapter mCommentsAdapter;
    @Inject
    ViewModelProviderFactory factory;
    @Inject
    LinearLayoutManager mLayoutManager;

    private ActivityDetailsBinding mActivityDetailsBinding;

    private DetailsViewModel mDetailsViewModel;
    private Toolbar mToolbar;
    private String pendingPhone;

    public static Intent newIntent(Context context, String postId) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(BUNDLE_POST_ID,postId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportPostponeEnterTransition();

        mActivityDetailsBinding = getViewDataBinding();
        mDetailsViewModel.setNavigator(this);

        mToolbar = mActivityDetailsBinding.toolbar;

        setUp();
    }

    private void setUp() {
        setSupportActionBar(mToolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mActivityDetailsBinding.cardPostImage.setTransitionName("cardPostImage".concat(" ").concat(getBundlePostId()));
            mActivityDetailsBinding.cardAvatarImage.setTransitionName("cardAvatarImage".concat(" ").concat(getBundlePostId()));
        }

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityDetailsBinding.recyclewComments.setLayoutManager(mLayoutManager);
        mActivityDetailsBinding.recyclewComments.setItemAnimator(new DefaultItemAnimator());
        mActivityDetailsBinding.recyclewComments.setAdapter(mCommentsAdapter);

        mDetailsViewModel.getCommentListMutableLiveData().observe(this,
                comments -> mCommentsAdapter.addItems(comments));

        mDetailsViewModel.notifyBundlePostId(getBundlePostId());
    }

    private String getBundlePostId(){
        if(getIntent().getExtras()!= null && getIntent().getExtras().containsKey(BUNDLE_POST_ID))
            return getIntent().getExtras().getString(BUNDLE_POST_ID);
        return "";
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_details;
    }

    @Override
    public DetailsViewModel getViewModel() {
        mDetailsViewModel =   ViewModelProviders.of(this, factory).get(DetailsViewModel.class);
        return mDetailsViewModel;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case REQUEST_PERMISSION_PHONE_CALL:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    launchPhoneCall(pendingPhone);
                } else {
                    AppLogger.d("TAG", "Call Permission Not Granted");
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void launchMail(String mail) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, mail);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.send_mail_extra_text_sample));

        startActivity(Intent.createChooser(intent, "Send Email"));
    }

    @Override
    public void launchGeoMaps(String lat, String lng) {
        loadWebUrl(String.format("https://www.google.com/maps/search/?api=1&query=%s,%s",lat,lng));
    }

    @Override
    public void launchPhoneCall(String phone) {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            pendingPhone = phone;
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_PERMISSION_PHONE_CALL);
        } else {
            pendingPhone = "";
            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:".concat(phone))));
        }
    }

    @Override
    public void loadWebUrl(String webUrl) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        Uri web = Uri.parse(webUrl);
        i.setData(Uri.parse(web.getHost() == null ? String.format("http://www.google.com/search?q=%s",webUrl) : webUrl));
        startActivity(i);
    }
}
