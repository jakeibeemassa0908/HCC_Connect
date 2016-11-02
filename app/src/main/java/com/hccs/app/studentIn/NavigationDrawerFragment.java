package com.hccs.app.studentIn;


import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class NavigationDrawerFragment extends Fragment {

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks mCallbacks;

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private View mFragmentContainerView;

    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;

    private ArrayList<DrawerItem> mDrawerItems;

    private DrawerItem  publiclinks,header,dashboard,financials,
            schedule,academics,enrollment,myInfo,widgets,EGLS,about
            ,courseCat,classSearch,campusMap,news,library,calendar,fullSite;

    public NavigationDrawerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Read in the flag indicating whether or not the user has demonstrated awareness of the
        // drawer. See PREF_USER_LEARNED_DRAWER for details.
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }

        // Select either the default item (0) or the last selected item.
        selectItem(mCurrentSelectedPosition);
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mDrawerListView = (ListView) inflater.inflate(
                R.layout.fragment_navigation_drawer, container, false);
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });

        mDrawerItems = new ArrayList<DrawerItem>();
        createDrawerItems();

        mDrawerListView.setAdapter(new DrawerAdapter(mDrawerItems));
        mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
        return mDrawerListView;
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    // The user manually opened the drawer; store this flag to prevent auto-showing
                    // the navigation drawer automatically in the future.
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }

                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (mDrawerListView != null) {
            mDrawerListView.setItemChecked(position, true);
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
        if (mDrawerLayout != null && isDrawerOpen()) {
            inflater.inflate(R.menu.global, menu);
            showGlobalContextActionBar();
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Per the navigation drawer design guidelines, updates the action bar to show the global app
     * 'context', rather than just what's in the current screen.
     */
    private void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle(R.string.hcc);
    }

    private ActionBar getActionBar() {
        return getActivity().getActionBar();
    }

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public  interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(int position);
    }

    private void createDrawerItems(){
        header = new DrawerItem();
        header.setType(DrawerItem.TYPE_HEADER);
        mDrawerItems.add(header);



        dashboard = new DrawerItem();
        dashboard.setTitle(getString(R.string.title_section1));
        dashboard.setImage(R.drawable.ic_action_action_home);
        mDrawerItems.add(dashboard);


        financials = new DrawerItem();
        financials.setTitle(getString(R.string.title_section2));
        financials.setImage(R.drawable.ic_action_action_trending_up);
        mDrawerItems.add(financials);


        schedule = new DrawerItem();
        schedule.setTitle(getString(R.string.title_section3));
        schedule.setImage(R.drawable.ic_action_action_today);
        mDrawerItems.add(schedule);

        academics = new DrawerItem();
        academics.setTitle(getString(R.string.title_section4));
        academics.setImage(R.drawable.ic_action_editor_border_color);
        mDrawerItems.add(academics);

        enrollment = new DrawerItem();
        enrollment.setTitle(getString(R.string.title_section5));
        enrollment.setImage(R.drawable.ic_action_social_school);
        mDrawerItems.add(enrollment);

        myInfo = new DrawerItem();
        myInfo.setTitle(getString(R.string.title_section6));
        myInfo.setImage(R.drawable.ic_action_social_person);
        mDrawerItems.add(myInfo);

        widgets = new DrawerItem();
        widgets.setTitle(getString(R.string.title_section7));
        widgets.setImage(R.drawable.ic_action_action_settings);
        mDrawerItems.add(widgets);

        EGLS = new DrawerItem();
        EGLS.setTitle(getString(R.string.title_section8));
        EGLS.setImage(R.drawable.ic_action_social_people);
        mDrawerItems.add(EGLS);

        about = new DrawerItem();
        about.setTitle(getString(R.string.title_section9));
        about.setImage(R.drawable.ic_action_alert_error);
        mDrawerItems.add(about);

        publiclinks = new DrawerItem();
        publiclinks.setTitle(getString(R.string.public_links));
        publiclinks.setType(DrawerItem.TYPE_TITLE);
        mDrawerItems.add(publiclinks);

        courseCat = new DrawerItem();
        courseCat.setTitle(getString(R.string.title_section11));
        courseCat.setImage(R.drawable.ic_action_maps_local_library);
        mDrawerItems.add(courseCat);

        classSearch = new DrawerItem();
        classSearch.setTitle(getString(R.string.title_section12));
        classSearch.setImage(R.drawable.ic_action_action_search);
        mDrawerItems.add(classSearch);

        campusMap = new DrawerItem();
        campusMap.setTitle(getString(R.string.title_section13));
        campusMap.setImage(R.drawable.ic_action_maps_place);
        mDrawerItems.add(campusMap);

        news = new DrawerItem();
        news.setTitle(getString(R.string.title_section14));
        news.setImage(R.drawable.ic_action_maps_local_library);
        mDrawerItems.add(news);

        library = new DrawerItem();
        library.setTitle(getString(R.string.title_section15));
        library.setImage(R.drawable.ic_action_maps_local_library);
        mDrawerItems.add(library);

        calendar = new DrawerItem();
        calendar.setTitle(getString(R.string.title_section16));
        calendar.setImage(R.drawable.ic_action_notification_event_note);
        mDrawerItems.add(calendar);

        fullSite = new DrawerItem();
        fullSite.setTitle(getString(R.string.title_section17));
        fullSite.setImage(R.drawable.ic_action_action_language);
        mDrawerItems.add(fullSite);

    }

    public class DrawerAdapter extends ArrayAdapter<DrawerItem>{

        public DrawerAdapter(ArrayList<DrawerItem> items) {
            super(getActivity(),0,items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            DrawerItem item = getItem(position);
                if(item.getType() ==DrawerItem.TYPE_HEADER){
                    convertView= getActivity().getLayoutInflater().inflate(R.layout.drawer_list_header,null);

                }else if(item.getType() == DrawerItem.TYPE_TITLE){
                    convertView = getActivity().getLayoutInflater().inflate(R.layout.drawer_title,null);
                }else{
                    convertView = getActivity().getLayoutInflater().inflate(R.layout.drawer_item,null);
                }

                if(item.getType()==DrawerItem.TYPE_HEADER){
                    TextView login_out = (TextView)convertView.findViewById(R.id.login_logout);
                    login_out.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDrawerLayout.closeDrawer(mFragmentContainerView);
                            mCallbacks.onNavigationDrawerItemSelected(-1);
                        }
                    });

                }else if (item.getType() == DrawerItem.TYPE_TITLE){
                    TextView text = (TextView)convertView.findViewById(R.id.drawer_title);
                    text.setText(item.getTitle());

                }else{
                    TextView text = (TextView)convertView.findViewById(R.id.drawerItem);
                    text.setText(item.getTitle());
                    ImageView icon = (ImageView)convertView.findViewById(R.id.icon);
                    if(item.getImage()!=-1){
                        icon.setImageResource(item.getImage());
                    }
                }

            return convertView;
        }
    }
}
