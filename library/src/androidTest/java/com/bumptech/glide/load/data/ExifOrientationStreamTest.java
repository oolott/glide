package com.bumptech.glide.load.data;

import static com.google.common.truth.Truth.assertThat;

import com.bumptech.glide.load.resource.bitmap.ImageHeaderParser;
import com.bumptech.glide.testutil.TestResourceUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.io.InputStream;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, emulateSdk = 18)
public class ExifOrientationStreamTest {
  private InputStream openOrientationExample(boolean isLandscape, int item) {
    String filePrefix = isLandscape ? "Landscape" : "Portrait";
    return TestResourceUtil.openResource(getClass(),
        "exif-orientation-examples/" + filePrefix + "_" + item + ".jpg");
  }

  @Test
  public void testIncludesGivenExifOrientation() throws IOException {
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        InputStream toWrap = openOrientationExample(true /*isLandscape*/, j + 1);
        InputStream wrapped = new ExifOrientationStream(toWrap, i);
        ImageHeaderParser parser = new ImageHeaderParser(wrapped);
        assertThat(parser.getOrientation()).isEqualTo(i);

        toWrap = openOrientationExample(false /*isLandscape*/, j + 1);
        wrapped = new ExifOrientationStream(toWrap, i);
        parser = new ImageHeaderParser(wrapped);
        assertThat(parser.getOrientation()).isEqualTo(i);
      }
    }
  }
}