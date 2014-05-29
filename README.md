# Android Threading Library

Library to provide some helpful threading support.

## BaseAsyncLoader

Use this when you want basic loader support with broadcasts to trigger reload.  To load data, simply extend loadInBackground.  To listen to specific broadcasts,
extend either getBroadcastString, for a single broadcast string, or getBroadcastStrings, for multiple.  Whenever a broadcast is sent with those strings,
 the loader will start a refresh of data.

## DelayedBroadcastReceiver

This one is a bit trickier to explain.  Essentially, to avoid lifecycle issues with Activity and Fragment, we try to avoid using AsyncTask for background
operations.  A simple way to do that is to do something in a deep background thread, and send a broadcast when its done.  If the Fractivity is
listening for that broadcast, it can react.

In general, you want to register receivers in onResume, and unreg in onPause.  It probably wouldn't be good if UI code ran while the screen wasn't
active.  However, if the broadcast is sent while your UI isn't active, if you come back, you'll have missed it.

You could implement this with sticky broadcast, but that's system global, and I'm not 100% sure how long they stay, and if they'll just pile
up in the OS.  DelayedBroadcastReceiver can be either active or passive.  If active, your UI is running, and broadcasts are sent as usual.  If passive,
they get queued until marked active again.

### How to use

Register/unregister the DBR in onCreate/onDestroy.  In onResume/onPause, call markActive/markInactive.

### Thoughts

This is pretty new.  There's probably a WAY better way to do this kind of thing.  It may not be true that UI operations will cause issues with inactive
apps, so we can reg/unreg receivers in onCreate/onDestroy directly, although I'm pretty sure that's a bad idea.

Also, right now events are queued, but its reasonable to assume in many cases you'd only need the last one.  If you're sending many broadcasts,
it might make sense to change this to configure the behavior.

# DataProcessor

Background thread processor.  Generally just call the static method runProcess with a Runnable param.  Extend BaseOp for some help with sending out
result broadcasts.