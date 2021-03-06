<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".Teacher.StaffMainActivity">
    <com.google.android.material.appbar.AppBarLayout
        android:id="@+id/appbar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <RelativeLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content">
            <RelativeLayout
                android:id="@+id/tv"
                android:layout_width="match_parent"
                android:layout_height="wrap_content">

                <TextView
                    android:layout_marginLeft="10dp"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_centerVertical="true"
                    android:text="Teacher Dashboard"
                    android:textColor="@color/white"
                    android:textSize="20sp"
                    android:textStyle="bold" />

                <ImageView
                    android:layout_width="40dp"
                    android:layout_height="40dp"
                    android:layout_alignParentRight="true"
                    android:layout_marginRight="20dp"
                    android:padding="5dp"
                    android:src="@drawable/teacher" />
            </RelativeLayout>

            <com.google.android.material.tabs.TabLayout
                android:id="@+id/teacher_tabs"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_below="@id/tv"
                android:layout_marginTop="5dp"
                android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar" />
        </RelativeLayout>

    </com.google.android.material.appbar.AppBarLayout>

    <androidx.viewpager.widget.ViewPager
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_below="@id/appbar"
        app:layout_behavior="com.google.android.material.appbar.AppBarLayout$scrollingViewBehavior"
        android:id="@+id/teacher_viewpager"/>

</RelativeLayout>                                                                                                                                                                                                                                                                                                                                         