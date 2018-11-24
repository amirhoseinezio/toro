/*
 * Copyright (c) 2018 Nam Nguyen, nam@ene.im
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

package im.ene.toro.exoplayer;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.TransferListener;

/**
 * Abstract the {@link DefaultBandwidthMeter}, provide a wider use.
 *
 * @author eneim (2018/01/26).
 * @since 3.4.0
 * @deprecated In 3.7.0 Toro suggests to use the mechanism of Providers. This class is no longer
 * required there.
 */

@Deprecated @SuppressWarnings("WeakerAccess") //
public final class BaseMeter<T extends BandwidthMeter, S extends TransferListener>
    implements BandwidthMeter, TransferListener {

  @NonNull protected final T bandwidthMeter;
  @NonNull protected final TransferListener transferListener;

  @SuppressWarnings({ "WeakerAccess" }) //
  @Deprecated public BaseMeter(@NonNull T bandwidthMeter,
      @SuppressWarnings("unused") @NonNull S transferListener) {
    this.bandwidthMeter = bandwidthMeter;
    //noinspection ConstantConditions,unchecked
    this.transferListener = bandwidthMeter.getTransferListener();
  }

  public BaseMeter(@NonNull T bandwidthMeter) {
    this.bandwidthMeter = bandwidthMeter;
    //noinspection ConstantConditions,unchecked
    this.transferListener = this.bandwidthMeter.getTransferListener();
  }

  @Override public long getBitrateEstimate() {
    return bandwidthMeter.getBitrateEstimate();
  }

  @Override @Nullable public TransferListener getTransferListener() {
    return bandwidthMeter.getTransferListener();
  }

  @Override public void addEventListener(Handler eventHandler, EventListener eventListener) {
    bandwidthMeter.addEventListener(eventHandler, eventListener);
  }

  @Override public void removeEventListener(EventListener eventListener) {
    bandwidthMeter.removeEventListener(eventListener);
  }

  @Override
  public void onTransferInitializing(DataSource source, DataSpec dataSpec, boolean isNetwork) {
    transferListener.onTransferInitializing(source, dataSpec, isNetwork);
  }

  @Override public void onTransferStart(DataSource source, DataSpec dataSpec, boolean isNetwork) {
    transferListener.onTransferStart(source, dataSpec, isNetwork);
  }

  @Override public void onBytesTransferred(DataSource source, DataSpec dataSpec, boolean isNetwork,
      int bytesTransferred) {
    transferListener.onBytesTransferred(source, dataSpec, isNetwork, bytesTransferred);
  }

  @Override public void onTransferEnd(DataSource source, DataSpec dataSpec, boolean isNetwork) {
    transferListener.onTransferEnd(source, dataSpec, isNetwork);
  }
}
