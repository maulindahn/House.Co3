package com.example.hana.rentcostumes.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ProgressDialog;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.example.hana.rentcostumes.R;
import com.example.hana.rentcostumes.Utils;
import com.example.hana.rentcostumes.adapter.FeedAdapter;
import com.example.hana.rentcostumes.adapter.FeedItemAnimator;
import com.example.hana.rentcostumes.fragment.ExploreFragment;
import com.example.hana.rentcostumes.fragment.FavoriteCostumeFragment;
import com.example.hana.rentcostumes.fragment.HelpFragment;
import com.example.hana.rentcostumes.fragment.NotificationsFragment;
import com.example.hana.rentcostumes.fragment.RentFragment;
import com.example.hana.rentcostumes.fragment.RentedCostumeFragment;
import com.example.hana.rentcostumes.fragment.SettingFragment;
import com.example.hana.rentcostumes.fragment.UsedCostumeFragment;
import com.example.hana.rentcostumes.view.FeedContextMenu;
import com.example.hana.rentcostumes.view.FeedContextMenuManager;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseDrawerActivity implements FeedAdapter.OnFeedItemClickListener,
        FeedContextMenu.OnFeedContextMenuItemClickListener {
    //, NavigationView.OnNavigationItemSelectedListener {
    public static final String ACTION_SHOW_LOADING_ITEM = "action_show_loading_item";

    private static final int ANIM_DURATION_TOOLBAR = 300;
    private static final int ANIM_DURATION_FAB = 400;

    @BindView(R.id.rvFeed) RecyclerView rvFeed;
    @BindView(R.id.btnCreate) FloatingActionButton fabCreate;
    @BindView(R.id.content) CoordinatorLayout clContent;

    private FeedAdapter feedAdapter;

    private boolean pendingIntroAnimation;

    //SessionActivity sessionActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupFeed();

        if (savedInstanceState == null) {
            pendingIntroAnimation = true;
        } else {
            feedAdapter.updateItems(false);
        }

        //sessionActivity = new SessionActivity();
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        //        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawer.setDrawerListener(toggle);
        //toggle.syncState();

        //NavigationView navigationView = (NavigationView) findViewById(R.id.vNavigation);
        //navigationView.setNavigationItemSelectedListener(this);
    }

    private void setupFeed() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        rvFeed.setLayoutManager(linearLayoutManager);

        feedAdapter = new FeedAdapter(this);
        feedAdapter.setOnFeedItemClickListener(this);
        rvFeed.setAdapter(feedAdapter);
        rvFeed.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                FeedContextMenuManager.getInstance().onScrolled(recyclerView, dx, dy);
            }
        });
        rvFeed.setItemAnimator(new FeedItemAnimator());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (ACTION_SHOW_LOADING_ITEM.equals(intent.getAction())) {
            showFeedLoadingItemDelayed();
        }
    }

    private void showFeedLoadingItemDelayed() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rvFeed.smoothScrollToPosition(0);
                feedAdapter.showLoadingView();
            }
        }, 500);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (pendingIntroAnimation) {
            pendingIntroAnimation = false;
            startIntroAnimation();
        } //else {
            //getMenuInflater().inflate(R.menu.menu_main, menu);
            //return true;
        //}
        return true;
    }

    //@Override
    //public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
    //    int id = item.getItemId();

        //noinspection SimplifiableIfStatement
    //    if (id == R.id.action_inbox) {
    //        return true;
    //    }

    //    return super.onOptionsItemSelected(item);
    //}

    private void startIntroAnimation() {
        fabCreate.setTranslationY(2 * getResources().getDimensionPixelOffset(R.dimen.btn_fab_size));

        int actionbarSize = Utils.dpToPx(56);
        getToolbar().setTranslationY(-actionbarSize);
        getIvLogo().setTranslationY(-actionbarSize);
        getInboxMenuItem().getActionView().setTranslationY(-actionbarSize);

        getToolbar().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300);
        getIvLogo().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(400);
        getInboxMenuItem().getActionView().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        startContentAnimation();
                    }
                })
                .start();
    }

    private void startContentAnimation() {
        fabCreate.animate()
                .translationY(0)
                .setInterpolator(new OvershootInterpolator(1.f))
                .setStartDelay(300)
                .setDuration(ANIM_DURATION_FAB)
                .start();
        feedAdapter.updateItems(true);
    }

    @Override
    public void onCommentsClick(View v, int position) {
        final Intent intent = new Intent(this, CommentsActivity.class);
        int[] startingLocation = new int[2];
        v.getLocationOnScreen(startingLocation);
        intent.putExtra(CommentsActivity.ARG_DRAWING_START_LOCATION, startingLocation[1]);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    public void onMoreClick(View v, int itemPosition) {
        FeedContextMenuManager.getInstance().toggleContextMenuFromView(v, itemPosition, this);
    }

    @Override
    public void onProfileClick(View v) {
        int[] startingLocation = new int[2];
        v.getLocationOnScreen(startingLocation);
        startingLocation[0] += v.getWidth() / 2;
        UserProfilActivity.startUserProfileFromLocation(startingLocation, this);
        overridePendingTransition(0, 0);
    }

    @Override
    public void onReportClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @Override
    public void onSharePhotoClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @Override
    public void onCopyShareUrlClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @Override
    public void onCancelClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @OnClick(R.id.btnCreate)
    public void onTakePhotoClick() {
        int[] startingLocation = new int[2];
        fabCreate.getLocationOnScreen(startingLocation);
        startingLocation[0] += fabCreate.getWidth() / 2;
        TakePhotoActivity.startCameraFromLocation(startingLocation, this);
        overridePendingTransition(0, 0);
    }

    public void showLikedSnackbar() {
        Snackbar.make(clContent, "Liked!", Snackbar.LENGTH_SHORT).show();
    }

    //@Override
    //public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
    //    int id = item.getItemId();
    //    Fragment fragment = null;
    //    Class fragmentClass = null;
    //    //handle fragment
    //    if (id == R.id.vNavigation) {
    //        fragmentClass = ExploreFragment.class;
     //   } else if (id == R.id.nav_gallery) {
     //       fragmentClass = RentFragment.class;
    //    } else if (id == R.id.notification) {
    //        fragmentClass = NotificationsFragment.class;
    //    } else if (id == R.id.rentedcostumes) {
     //       fragmentClass = RentedCostumeFragment.class;
     //   } else if (id == R.id.usedcostumes) {
     //       fragmentClass = UsedCostumeFragment.class;
     //   } else if (id == R.id.favorite) {
     //       fragmentClass = FavoriteCostumeFragment.class;
     //   } else if (id == R.id.menuhelp) {
     //       fragmentClass = HelpFragment.class;
     //   } else if (id == R.id.setting) {
     //       fragmentClass = SettingFragment.class;
     //   } else if (id == R.id.signout){
     //       final ProgressDialog progressDialog = new ProgressDialog(this);
     //       progressDialog.setIndeterminate(true);
      //      progressDialog.setMessage("Logging Out...");
     //       progressDialog.show();
     //       new android.os.Handler().postDelayed(
     //               new Runnable() {
     //                   public void run() {
     //                       progressDialog.dismiss();
     //                       sessionActivity.clearPreferences(getApplicationContext(), "status");
     //                       Intent intent = new Intent(MainActivity.this, LoginActivity.class);
     //                       startActivity(intent);
                            //overridePendingTransition(R.anim.fadein,R.anim.fadeout);

     //                   }
     //               }, 2000);
     //   }
     //   try {
     //       fragment = (Fragment)fragmentClass.newInstance();
     //   } catch (Exception e){
     //       e.printStackTrace();
     //   }

     //   FragmentManager fragmentManager = getSupportFragmentManager();
     //   fragmentManager.beginTransaction().replace(R.id.flContentRoot, fragment).commit();

     //   DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
     //   drawer.closeDrawer(GravityCompat.START);
     //   return true;
    //}
}
