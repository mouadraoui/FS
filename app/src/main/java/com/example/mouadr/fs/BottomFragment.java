package com.example.mouadr.fs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public
class BottomFragment extends Fragment
{

	public BottomFragment()
	{
	}

	@Override
	public
	View onCreateView(
		LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
	)
	{
		View rootView = inflater.inflate( R.layout.fragment_bottom, container, false );
		return rootView;
	}
}
