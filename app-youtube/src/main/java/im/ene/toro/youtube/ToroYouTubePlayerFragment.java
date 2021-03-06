/*
 * Copyright (c) 2017 Nam Nguyen, nam@ene.im
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package im.ene.toro.youtube;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerContainerView;

/**
 * @author eneim (2017/12/07).
 */

public class ToroYouTubePlayerFragment extends Fragment implements YouTubePlayer.Provider {

  private static final String STATE_KEY = "ToroYouTubePlayerFragment.KEY_PLAYER_VIEW_STATE";

  // This instance lives out of the lifecycle callbacks, but must be released and cleared before
  // any destroying event (onDestroyView/onDestroy)
  private YouTubePlayerHelper playerHelper;

  /* package */ void setPlayerHelper(YouTubePlayerHelper playerHelper) {
    this.playerHelper = playerHelper;
  }

  @SuppressWarnings("WeakerAccess")
  public static ToroYouTubePlayerFragment newInstance() {
    return new ToroYouTubePlayerFragment();
  }

  @SuppressWarnings("WeakerAccess")
  YouTubePlayerContainerView containerView;

  @NonNull
  @Override public String toString() {
    return "YouT:Fragment{" + "helper=" + playerHelper + '}';
  }

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    this.containerView = (YouTubePlayerContainerView) inflater
        .inflate(R.layout.widget_youtube_container, container, false);
    return containerView;
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    Bundle playerState =
        savedInstanceState != null ? savedInstanceState.getBundle(STATE_KEY) : null;
    containerView.initPlayer(getViewLifecycleOwner(), playerState);

    if (this.playerHelper != null) this.playerHelper.ytFragment = this;
  }

  @Override public void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    Bundle playerState = containerView.getPlayerState();
    if (playerState != null) {
      outState.putBundle(STATE_KEY, playerState);
    }
  }

  @Override public void onStop() {
    super.onStop();
    // A stopped player need to be release or else it will throw error when resume. It is weird ...
    if (this.playerHelper != null) this.playerHelper.release();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    if (this.playerHelper != null) {
      this.playerHelper.release();
      this.playerHelper.ytFragment = null;
      this.playerHelper = null;
    }
  }

  @Override
  public void initialize(String apiKey, YouTubePlayer.OnInitializedListener onInitializedListener) {
    this.containerView.initialize(apiKey, onInitializedListener);
  }
}
