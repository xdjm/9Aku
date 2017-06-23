package com.xd.commander.aku.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.xd.commander.aku.R;
import com.xd.commander.aku.adapter.AdapterExpandedItem;
import com.xd.commander.aku.base.BaseFragment;
import com.xd.commander.aku.bean.Sort0Item;
import com.xd.commander.aku.bean.Sort1Item;
import java.util.ArrayList;

import butterknife.BindView;

public class FragmentSort extends BaseFragment {
    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    private ArrayList<MultiItemEntity> list;
    private String[][] sort11=
            {
                    {"2D Engines (10)1"}
                    , {"3D Engines (12)1"}
                    , {"9-Patch (4)1"}
                    , {"Action Bars (24)1",
                    "Activities (33)1",
                    "ADB (13)1",
                    "Advertisements (14)1",
                    "Analytics (10)1",
                    "Animations (99)1",
                    "ANR (1)1",
                    "AOP (3)1",
                    "API (71)1",
                    "APK (10)1",
                    "APT (79)1",
                    "Architecture (45)1",
                    "Audio (38)1",
                    "Autocomplete (15)1"}
                    , {"Background Processing (52)1",
                    "Backward Compatibility (12)1",
                    "Badges (14)1",
                    "Bar Codes (9)1",
                    "Benchmarking (5)1",
                    "Bitmaps (9)1",
                    "Bluetooth (18)1",
                    "Blur Effects (16)1",
                    "Bread Crumbs (4)1",
                    "BRMS (1)1",
                    "Browser Extensions (5)1",
                    "Build Systems (9)1",
                    "Bundles (4)1",
                    "Buttons (70)1"},
                    {"Caching (24)1",
                            "Camera (22)1",
                            "Canvas (4)1",
                            "Cards (19)1",
                            "Carousels (10)1",
                            "Changelog (6)1",
                            "Checkboxes (33)1",
                            "Cloud Storages (5)1",
                            "Color Analysis (5)1",
                            "Color Pickers (31)1",
                            "Colors (11)1",
                            "Comet Push (1)1",
                            "Compass Sensors (2)1",
                            "Content Providers (17)1",
                            "Crash Reports (14)1",
                            "Credit Cards (10)1",
                            "Credits (6)1",
                            "CSV (1)1",
                            "Curl/Flip Effects (5)1"},
                    {"Data Binding (18)1",
                            "Data Generators (8)1",
                            "Data Structures (18)1",
                            "Database (40)1",
                            "Database Browsers (9)1",
                            "Date and Time Pickers (71)1",
                            "Debugging (19)1",
                            "Decompilers (8)1",
                            "Deep Links (2)1",
                            "Dependency Injections (32)1",
                            "Design (30)1",
                            "Design Patterns (28)1",
                            "Dex (14)1",
                            "Dialogs (50)1",
                            "Distributed Computing (1)1",
                            "Distribution Platforms (4)1",
                            "Download Managers (13)1",
                            "Drawables (17)1"},
                    {"Emoji (11)1",
                            "Emulators (3)1",
                            "EPUB (3)1",
                            "Equalizers and Visualizations (5)1",
                            "Event Buses (21)1",
                            "Exception Handling (11)1"},
                    {"Face Recognition (4)1",
                            "Feedback and Ratings (8)1",
                            "File System (7)1",
                            "File/Directory Choosers (15)1",
                            "Fingerprint (7)1",
                            "Floating Action Buttons (29)1",
                            "Fonts (41)1",
                            "Forms (10)1",
                            "Fragments (15)1",
                            "FRP (40)1",
                            "FSM (4)1",
                            "Functional Programming (6)1"},
                    {"Gamepads (4)1",
                            "Games (1)1",
                            "Gestures (19)1",
                            "GIF (11)1",
                            "Glow Pad Views (1)1",
                            "Gradle Plugins (145)1",
                            "Graphics (37)1",
                            "Grid Views (16)1"},
                    {"Highlighting (9)1",
                            "HTML (8)1",
                            "HTTP Mocking (15)1"},
                    {"Icons (25)1",
                            "IDE (4)1",
                            "IDE Plugins (67)1",
                            "Image Croppers (19)1",
                            "Image Loaders (22)1",
                            "Image Pickers (42)1",
                            "Image Processing (15)1",
                            "Image Views (82)1",
                            "Instrumentation (8)1",
                            "Intents (16)1"},
                    {"Job Schedulers (6)1",
                            "JSON (26)1"},
                    {"Keyboard (9)1",
                            "Kotlin (11)1"},
                    {"Layouts (140)1",
                            "List View Sections (18)1",
                            "List Views (36)1",
                            "Localization (7)1",
                            "Location (24)1",
                            "Lock Patterns (13)1",
                            "Logcat (8)1",
                            "Logging (60)1"},
                    {"Mails (3)1",
                            "Maps (36)1",
                            "Markdown (9)1",
                            "Mathematics (3)1",
                            "Maven Plugins (1)1",
                            "MBaaS (1)1",
                            "Media (17)1",
                            "Menus (63)1",
                            "Messaging (5)1",
                            "MIME (2)1",
                            "Mobile Web Frameworks (5)1"},
                    {"Native Image Processing (10)1",
                            "Navigation (27)1",
                            "NDK (1)1",
                            "Networking (70)1",
                            "NFC (4)1",
                            "NoSQL (24)1",
                            "Number Pickers (12)1"},
                    {"OAuth (14)1",
                            "Object Mocking (8)1",
                            "OCR Engines (5)1",
                            "OpenGL (1)1",
                            "ORM (43)1",
                            "Other Pickers (15)1"},
                    {"Parallax List Views (8)1",
                            "Parcelables (7)1",
                            "Particle Systems (2)1",
                            "Password Inputs (17)1",
                            "PDF (9)1",
                            "Permissions (47)1",
                            "Physics Engines (5)1",
                            "Platforms (12)1",
                            "Plugin Frameworks (3)1",
                            "Preferences (71)1",
                            "Progress Indicators (142)1",
                            "ProGuard (5)1",
                            "Properties (1)1",
                            "Protocol Buffer (6)1",
                            "Pull To Refresh (39)1",
                            "Purchases (16)1",
                            "Push/Pull Notifications (22)1"},
                    {"QR Codes (9)1",
                            "Quick Return (3)1"},
                    {"Radio Buttons (6)1",
                            "Range Bars (24)1",
                            "Ratings (26)1",
                            "Recycler Views (104)1",
                            "Resources (7)1",
                            "REST (29)1",
                            "Ripple Effects (8)1",
                            "RSS (9)1"},
                    {"Screenshots (7)1",
                            "Scripting (6)1",
                            "Scroll Views (28)1",
                            "SDK (6)1",
                            "Search Inputs (16)1",
                            "Security (40)1",
                            "Sensors (18)1",
                            "Services (2)1",
                            "Showcase Views (27)1",
                            "Signatures (4)1",
                            "Sliding Panels (31)1",
                            "Snackbars (10)1",
                            "SOAP (4)1",
                            "Social Networks (41)1",
                            "Spannable (7)1",
                            "Spinners (11)1",
                            "Splash Screens (3)1",
                            "SSH (2)1",
                            "Static Analysis Tools (19)1",
                            "Status Bars (6)1",
                            "Styling (2)1",
                            "SVG (13)1",
                            "System (31)1"},
                    {"Tags (24)1",
                            "TDD and BDD (30)1",
                            "Template Engines (5)1",
                            "Testing (27)1",
                            "Testing Tools (14)1",
                            "Text Formatting (16)1",
                            "Text Views (137)1",
                            "Text Watchers (2)1",
                            "Text-to-Speech (3)1",
                            "Toasts (23)1",
                            "Toolkits For Other PL (13)1",
                            "Tools (56)1",
                            "Tooltips (13)1",
                            "TV (2)1"},
                    {"Updaters (19)1",
                            "USB (6)1",
                            "Utils (107)1"},
                    {"Validation (30)1",
                            "Video (39)1",
                            "View Adapters (100)1",
                            "View Pagers (101)1",
                            "Views (168)1"},
                    {"Watch Face (1)1",
                            "Wearable Data Layer (8)1",
                            "Weather (11)1",
                            "Web Tools (3)1",
                            "Web Views (15)1",
                            "WebRTC (1)1",
                            "WebSockets (5)1",
                            "Wheel Widgets (12)1",
                            "Wi-Fi (10)1",
                            "Widgets (26)1",
                            "Windows (3)1",
                            "Wizards (9)1"},
                    {"XML (9)1",
                            "XMPP (2)1"},
                    {"YAML (4)1"},
                    {"ZIP Codes (1)1"}};
    private String[][] sort21 =
            {{      "2D Engines (1)2"}, {
                    "3D Engines (1)2"}, {
                    "Analytics (8)2"}, {
                    "Continuous Integration (1)2",
                    "Crash Reports (6)2"}, {
                    "Decompilers (1)2",
                    "Design (1)2",
                    "Distribution Platforms (2)2"}, {
                    "Emulators (1)2"}, {
                    "Feedback & Ratings (1)2"}, {
                    "Graphics (2)2"}, {
                    "IDE Plugins (2)2"}, {
                    "Image Processing (1)2"}, {
                    "List Views (1)2",
                    "Logcat (1)2"}, {
                    "Maps (1)2",
                    "MBaaS (8)2",
                    "Media (1)2",
                    "Messaging (1)2",
                    "Mobile Web Frameworks (1)2"}, {
                    "Networking (1)2"}, {
                    "OCR Engines (1)2",
                    "ORM (1)2"}, {
                    "PDF (5)2",
                    "Platforms (4)2",
                    "Purchases (1)2",
                    "Push/Pull Notifications (4)2"}, {
                    "QR Codes (2)2"}, {
                    "REST (1)2"}, {
                    "SSH (1)2"}, {
                    "Testing Tools (4)2",
                    "Tools (1)2"}, {
                    "Video (2)2"}, {
                    "WebRTC (1)2"}, {
                    "XMPP (1)2"}};
    public final  String[][] sort31 =
            {{      "Action Bars (2)3",
                    "Animations (31)3",
                    "API (10)3",
                    "APK (1)3",
                    "APT (2)3",
                    "Architecture (38)3",
                    "Audio (15)3"}, {
                    "Bar Codes (2)3",
                    "Bluetooth (3)3",
                    "Blur Effects (3)3",
                    "Buttons (2)3"}, {
                    "Camera (5)3",
                    "Canvas (1)3",
                    "Cards (3)3",
                    "Carousels (2)3",
                    "Checkboxes (2)3",
                    "Color Analysis (1)3",
                    "Colors (1)3",
                    "Conferences (5)3",
                    "Crash Reports (1)3",
                    "Credit Cards (1)3",
                    "Curl/Flip Effects (1)3"}, {
                    "Data Binding (7)3",
                    "Database (1)3",
                    "Date & Time Pickers (1)3",
                    "Design (11)3",
                    "Design Patterns (7)3",
                    "Distributed Computing (1)3",
                    "Download Managers (1)3",
                    "Drawables (3)3"}, {
                    "Emoji (3)3",
                    "Emulators (5)3",
                    "Event Buses (1)3",
                    "Exception Handling (1)3"}, {
                    "File System (1)3",
                    "File/Directory Choosers (1)3",
                    "Floating Action Buttons (3)3",
                    "Fonts (1)3",
                    "Fragments (1)3",
                    "FRP (7)3"}, {
                    "Games (1)3",
                    "Geocaching (1)3",
                    "GIF (1)3",
                    "Gradle Plugins (1)3",
                    "Graphics (2)3"}, {
                    "Image Croppers (2)3",
                    "Image Pickers (1)3",
                    "Image Processing (3)3",
                    "Image Views (4)3",
                    "Intents (1)3"}, {
                    "Job Schedulers (1)3",
                    "JSON (1)3"}, {
                    "Keyboard (3)3",
                    "Kotlin (3)3"}, {
                    "Layouts (6)3",
                    "Library Demos (4)3",
                    "List View Sections (1)3",
                    "List Views (5)3",
                    "Location (1)3",
                    "Logcat (2)3"}, {
                    "Mails (3)3",
                    "Maps (5)3",
                    "Markdown (1)3",
                    "Media (5)3",
                    "Menus (9)3",
                    "Messaging (11)3"}, {
                    "Navigation (2)3",
                    "Networking (1)3",
                    "NoSQL (2)3"}, {
                    "OCR Engines (1)3",
                    "OpenGL (3)3",
                    "Other Pickers (1)3"}, {
                    "Parallax List Views (1)3",
                    "PDF (2)3",
                    "Permissions (2)3",
                    "Preferences (1)3",
                    "Progress Indicators (7)3",
                    "ProGuard (1)3",
                    "Protocol Buffer (2)3",
                    "Pull To Refresh (3)3",
                    "Push/Pull Notifications (5)3"}, {
                    "QR Codes (1)3",
                    "Quick Return (1)3"}, {
                    "Recycler Views (11)3",
                    "Ripple Effects (1)3",
                    "RSS (5)3"}, {
                    "Screenshots (1)3",
                    "Scroll Views (1)3",
                    "Search Inputs (2)3",
                    "Security (7)3",
                    "Sensors (2)3",
                    "Showcase Views (3)3",
                    "Sliding Panels (5)3",
                    "Social Networks (20)3",
                    "SSH (2)3",
                    "Static Analysis Tools (3)3",
                    "Status Bars (1)3",
                    "System (3)3"}, {
                    "Tags (1)3",
                    "Task Managers (1)3",
                    "TDD & BDD (1)3",
                    "Testing (4)3",
                    "Testing Tools (1)3",
                    "Text Views (5)3",
                    "Text-to-Speech (4)3",
                    "Tools (3)3",
                    "Trainings (1)3",
                    "TV (2)3",
                    "Twitter (5)3"}, {
                    "Updaters (1)3",
                    "USB (3)3",
                    "Utils (2)3"}, {
                    "Video (7)3",
                    "View Pagers (4)3",
                    "Views (14)3"}, {
                    "Watch Face (1)3",
                    "Wearable Data Layer (1)3",
                    "Weather (4)3",
                    "Web Views (2)3",
                    "WebRTC (1)3",
                    "Widgets (1)3"}, {
                    "XMPP (4)3"}};
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_category;
    }

    public FragmentSort newInstance(int category) {
        FragmentSort newFragment = new FragmentSort();
        Bundle bundle = new Bundle();
        bundle.putInt("category", category);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            int category = args.getInt("category");
            list = generateData(category);
        }
    }

    @Override
    protected void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //适配器
        AdapterExpandedItem adapter = new AdapterExpandedItem(list);
        mRecyclerView.setAdapter(adapter);
        //TODO 获取FlexboxLayoutManager布局管理器的实例
        final FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager();
        //TODO 为设置recycle中的子项设置布局管理
        flexboxLayoutManager.setRecycleChildrenOnDetach(true);
        //TODO 设置子项宽度
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        //TODO 设置子项的整体重心
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        //TODO 为recyclerview设置FlexboxLayoutManager的布局管理器
        mRecyclerView.setLayoutManager(flexboxLayoutManager);
        /**
         * 全部展开adapter.expandAll();
         */
    }
    private ArrayList<MultiItemEntity> generateData(int category) {
        /**
         * sort0 大分类
         * sort1 小分类
         */
        ArrayList<MultiItemEntity> res=new ArrayList<>();
        switch (category) {
            case 1:
                String[] sort00 = new String[29];
                sort00[0] = "2";
                sort00[1] = "3";
                sort00[2] = "9";
                for (int i = 0; i <= 25; i++) {
                    sort00[i + 3] = String.valueOf((char) ('A' + i));
                }

                for (int i = 0; i < sort00.length; i++) {
                    Sort0Item lv0 = new Sort0Item(sort00[i]);
                    for (int j = 0; j < sort11[i].length; j++) {
                        Sort1Item lv1 = new Sort1Item(sort11[i][j]);
                        lv0.addSubItem(lv1);
                    }
                    res.add(lv0);
                }
                break;
            case 2:
                String[] sort10 = new String[]{"2","3","A","C","D","E","F","G","I","J","L","M","N"
                        ,"O","P","Q","R","S","T","V","W","X"};
                for (int i = 0; i < sort10.length; i++) {
                    Sort0Item lv0 = new Sort0Item(sort10[i]);
                    for (int j = 0; j < sort21[i].length; j++) {
                        Sort1Item lv1 = new Sort1Item(sort21[i][j]);
                        lv0.addSubItem(lv1);
                    }
                    res.add(lv0);
                }
                break;
            case 3:
                String[] sort_paid = new String[]{"A","B","C","D","E","F","G","I","J","K","L","M","N"
                ,"O","P","Q","R","S","T","U","V","W","X"};
                for (int i = 0; i < sort_paid.length; i++) {
                    Sort0Item lv0 = new Sort0Item(sort_paid[i]);
                    for (int j = 0; j < sort31[i].length; j++) {
                        Sort1Item lv1 = new Sort1Item(sort31[i][j]);
                        lv0.addSubItem(lv1);
                    }
                    res.add(lv0);
                }
                break;
        }
        return res;
    }
}

