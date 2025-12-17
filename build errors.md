--------- beginning of crash
2025-12-16 00:34:39.389 25954-25954 AndroidRuntime          pid-25954                            E  FATAL EXCEPTION: main (Fix with AI)
                                                                                                    Process: com.aks_labs.pixelflow, PID: 25954
                                                                                                    java.lang.IllegalArgumentException: Key "1000004137" was already used. If you are using LazyColumn/Row please make sure you provide a unique key for each item.
                                                                                                    	at androidx.compose.ui.layout.LayoutNodeSubcompositionsState.subcompose(SubcomposeLayout.kt:660)
                                                                                                    	at androidx.compose.ui.layout.LayoutNodeSubcompositionsState$Scope.subcompose(SubcomposeLayout.kt:1014)
                                                                                                    	at androidx.compose.foundation.lazy.layout.LazyLayoutMeasureScopeImpl.measure-0kLqBqw(LazyLayoutMeasureScope.kt:121)
                                                                                                    	at androidx.compose.foundation.lazy.grid.LazyGridMeasuredItemProvider.getAndMeasure-3p2s80s(LazyGridMeasuredItemProvider.kt:45)
                                                                                                    	at androidx.compose.foundation.lazy.grid.LazyGridMeasuredLineProvider.getAndMeasure(LazyGridMeasuredLineProvider.kt:80)
                                                                                                    	at androidx.compose.foundation.lazy.grid.LazyGridMeasureKt.measureLazyGrid-ZRKPzZ8(LazyGridMeasure.kt:151)
                                                                                                    	at androidx.compose.foundation.lazy.grid.LazyGridKt$rememberLazyGridMeasurePolicy$1$1.invoke-0kLqBqw(LazyGrid.kt:342)
                                                                                                    	at androidx.compose.foundation.lazy.grid.LazyGridKt$rememberLazyGridMeasurePolicy$1$1.invoke(LazyGrid.kt:183)
                                                                                                    	at androidx.compose.foundation.lazy.layout.LazyLayoutKt$LazyLayout$3$2$1.invoke-0kLqBqw(LazyLayout.kt:87)
                                                                                                    	at androidx.compose.foundation.lazy.layout.LazyLayoutKt$LazyLayout$3$2$1.invoke(LazyLayout.kt:80)
                                                                                                    	at androidx.compose.ui.layout.LayoutNodeSubcompositionsState$createMeasurePolicy$1.measure-3p2s80s(SubcomposeLayout.kt:866)
                                                                                                    	at androidx.compose.ui.node.InnerNodeCoordinator.measure-BRTryo0(InnerNodeCoordinator.kt:126)
                                                                                                    	at androidx.compose.foundation.AndroidOverscrollKt$StretchOverscrollNonClippingLayer$2.invoke-3p2s80s(AndroidOverscroll.kt:578)
                                                                                                    	at androidx.compose.foundation.AndroidOverscrollKt$StretchOverscrollNonClippingLayer$2.invoke(AndroidOverscroll.kt:577)
                                                                                                    	at androidx.compose.ui.layout.LayoutModifierImpl.measure-3p2s80s(LayoutModifier.kt:291)
                                                                                                    	at androidx.compose.ui.node.LayoutModifierNodeCoordinator.measure-BRTryo0(LayoutModifierNodeCoordinator.kt:116)
                                                                                                    	at androidx.compose.foundation.AndroidOverscrollKt$StretchOverscrollNonClippingLayer$1.invoke-3p2s80s(AndroidOverscroll.kt:562)
                                                                                                    	at androidx.compose.foundation.AndroidOverscrollKt$StretchOverscrollNonClippingLayer$1.invoke(AndroidOverscroll.kt:561)
                                                                                                    	at androidx.compose.ui.layout.LayoutModifierImpl.measure-3p2s80s(LayoutModifier.kt:291)
                                                                                                    	at androidx.compose.ui.node.LayoutModifierNodeCoordinator.measure-BRTryo0(LayoutModifierNodeCoordinator.kt:116)
                                                                                                    	at androidx.compose.ui.graphics.SimpleGraphicsLayerModifier.measure-3p2s80s(GraphicsLayerModifier.kt:646)
                                                                                                    	at androidx.compose.ui.node.LayoutModifierNodeCoordinator.measure-BRTryo0(LayoutModifierNodeCoordinator.kt:116)
                                                                                                    	at androidx.compose.foundation.layout.FillNode.measure-3p2s80s(Size.kt:698)
                                                                                                    	at androidx.compose.ui.node.LayoutModifierNodeCoordinator.measure-BRTryo0(LayoutModifierNodeCoordinator.kt:116)
                                                                                                    	at androidx.compose.foundation.layout.FillNode.measure-3p2s80s(Size.kt:698)
                                                                                                    	at androidx.compose.ui.node.LayoutModifierNodeCoordinator.measure-BRTryo0(LayoutModifierNodeCoordinator.kt:116)
                                                                                                    	at androidx.compose.ui.node.LayoutNodeLayoutDelegate$performMeasure$2.invoke(LayoutNodeLayoutDelegate.kt:1499)
                                                                                                    	at androidx.compose.ui.node.LayoutNodeLayoutDelegate$performMeasure$2.invoke(LayoutNodeLayoutDelegate.kt:1495)
                                                                                                    	at androidx.compose.runtime.snapshots.Snapshot$Companion.observe(Snapshot.kt:2299)
                                                                                                    	at androidx.compose.runtime.snapshots.SnapshotStateObserver$ObservedScopeMap.observe(SnapshotStateObserver.kt:467)
                                                                                                    	at androidx.compose.runtime.snapshots.SnapshotStateObserver.observeReads(SnapshotStateObserver.kt:230)
                                                                                                    	at androidx.compose.ui.node.OwnerSnapshotObserver.observeReads$ui_release(OwnerSnapshotObserver.kt:133)
                                                                                                    	at androidx.compose.ui.node.OwnerSnapshotObserver.observeMeasureSnapshotReads$ui_release(OwnerSnapshotObserver.kt:113)
                                                                                                    	at androidx.compose.ui.node.LayoutNodeLayoutDelegate.performMeasure-BRTryo0(LayoutNodeLayoutDelegate.kt:1495)
2025-12-16 00:34:50.657 26546-26546 AndroidRuntime          pid-26546                            E  FATAL EXCEPTION: main (Fix with AI)
                                                                                                    Process: com.aks_labs.pixelflow, PID: 26546
                                                                                                    java.lang.IllegalArgumentException: Key "1000004137" was already used. If you are using LazyColumn/Row please make sure you provide a unique key for each item.
                                                                                                    	at androidx.compose.ui.layout.LayoutNodeSubcompositionsState.subcompose(SubcomposeLayout.kt:660)
                                                                                                    	at androidx.compose.ui.layout.LayoutNodeSubcompositionsState$Scope.subcompose(SubcomposeLayout.kt:1014)
                                                                                                    	at androidx.compose.foundation.lazy.layout.LazyLayoutMeasureScopeImpl.measure-0kLqBqw(LazyLayoutMeasureScope.kt:121)
                                                                                                    	at androidx.compose.foundation.lazy.grid.LazyGridMeasuredItemProvider.getAndMeasure-3p2s80s(LazyGridMeasuredItemProvider.kt:45)
                                                                                                    	at androidx.compose.foundation.lazy.grid.LazyGridMeasuredLineProvider.getAndMeasure(LazyGridMeasuredLineProvider.kt:80)
                                                                                                    	at androidx.compose.foundation.lazy.grid.LazyGridMeasureKt.measureLazyGrid-ZRKPzZ8(LazyGridMeasure.kt:151)
                                                                                                    	at androidx.compose.foundation.lazy.grid.LazyGridKt$rememberLazyGridMeasurePolicy$1$1.invoke-0kLqBqw(LazyGrid.kt:342)
                                                                                                    	at androidx.compose.foundation.lazy.grid.LazyGridKt$rememberLazyGridMeasurePolicy$1$1.invoke(LazyGrid.kt:183)
                                                                                                    	at androidx.compose.foundation.lazy.layout.LazyLayoutKt$LazyLayout$3$2$1.invoke-0kLqBqw(LazyLayout.kt:87)
                                                                                                    	at androidx.compose.foundation.lazy.layout.LazyLayoutKt$LazyLayout$3$2$1.invoke(LazyLayout.kt:80)
                                                                                                    	at androidx.compose.ui.layout.LayoutNodeSubcompositionsState$createMeasurePolicy$1.measure-3p2s80s(SubcomposeLayout.kt:866)
                                                                                                    	at androidx.compose.ui.node.InnerNodeCoordinator.measure-BRTryo0(InnerNodeCoordinator.kt:126)
                                                                                                    	at androidx.compose.foundation.AndroidOverscrollKt$StretchOverscrollNonClippingLayer$2.invoke-3p2s80s(AndroidOverscroll.kt:578)
                                                                                                    	at androidx.compose.foundation.AndroidOverscrollKt$StretchOverscrollNonClippingLayer$2.invoke(AndroidOverscroll.kt:577)
                                                                                                    	at androidx.compose.ui.layout.LayoutModifierImpl.measure-3p2s80s(LayoutModifier.kt:291)
                                                                                                    	at androidx.compose.ui.node.LayoutModifierNodeCoordinator.measure-BRTryo0(LayoutModifierNodeCoordinator.kt:116)
                                                                                                    	at androidx.compose.foundation.AndroidOverscrollKt$StretchOverscrollNonClippingLayer$1.invoke-3p2s80s(AndroidOverscroll.kt:562)
                                                                                                    	at androidx.compose.foundation.AndroidOverscrollKt$StretchOverscrollNonClippingLayer$1.invoke(AndroidOverscroll.kt:561)
                                                                                                    	at androidx.compose.ui.layout.LayoutModifierImpl.measure-3p2s80s(LayoutModifier.kt:291)
                                                                                                    	at androidx.compose.ui.node.LayoutModifierNodeCoordinator.measure-BRTryo0(LayoutModifierNodeCoordinator.kt:116)
                                                                                                    	at androidx.compose.ui.graphics.SimpleGraphicsLayerModifier.measure-3p2s80s(GraphicsLayerModifier.kt:646)
                                                                                                    	at androidx.compose.ui.node.LayoutModifierNodeCoordinator.measure-BRTryo0(LayoutModifierNodeCoordinator.kt:116)
                                                                                                    	at androidx.compose.foundation.layout.FillNode.measure-3p2s80s(Size.kt:698)
                                                                                                    	at androidx.compose.ui.node.LayoutModifierNodeCoordinator.measure-BRTryo0(LayoutModifierNodeCoordinator.kt:116)
                                                                                                    	at androidx.compose.foundation.layout.FillNode.measure-3p2s80s(Size.kt:698)
                                                                                                    	at androidx.compose.ui.node.LayoutModifierNodeCoordinator.measure-BRTryo0(LayoutModifierNodeCoordinator.kt:116)
                                                                                                    	at androidx.compose.ui.node.LayoutNodeLayoutDelegate$performMeasure$2.invoke(LayoutNodeLayoutDelegate.kt:1499)
                                                                                                    	at androidx.compose.ui.node.LayoutNodeLayoutDelegate$performMeasure$2.invoke(LayoutNodeLayoutDelegate.kt:1495)
                                                                                                    	at androidx.compose.runtime.snapshots.Snapshot$Companion.observe(Snapshot.kt:2299)
                                                                                                    	at androidx.compose.runtime.snapshots.SnapshotStateObserver$ObservedScopeMap.observe(SnapshotStateObserver.kt:467)
                                                                                                    	at androidx.compose.runtime.snapshots.SnapshotStateObserver.observeReads(SnapshotStateObserver.kt:230)
                                                                                                    	at androidx.compose.ui.node.OwnerSnapshotObserver.observeReads$ui_release(OwnerSnapshotObserver.kt:133)
                                                                                                    	at androidx.compose.ui.node.OwnerSnapshotObserver.observeMeasureSnapshotReads$ui_release(OwnerSnapshotObserver.kt:113)
                                                                                                    	at androidx.compose.ui.node.LayoutNodeLayoutDelegate.performMeasure-BRTryo0(LayoutNodeLayoutDelegate.kt:1495)
2025-12-16 00:35:06.131 26630-26630 AndroidRuntime          pid-26630                            E  FATAL EXCEPTION: main (Fix with AI)
                                                                                                    Process: com.aks_labs.pixelflow, PID: 26630
                                                                                                    java.lang.IllegalArgumentException: Key "1000004129" was already used. If you are using LazyColumn/Row please make sure you provide a unique key for each item.
                                                                                                    	at androidx.compose.ui.layout.LayoutNodeSubcompositionsState.subcompose(SubcomposeLayout.kt:660)
                                                                                                    	at androidx.compose.ui.layout.LayoutNodeSubcompositionsState$Scope.subcompose(SubcomposeLayout.kt:1014)
                                                                                                    	at androidx.compose.foundation.lazy.layout.LazyLayoutMeasureScopeImpl.measure-0kLqBqw(LazyLayoutMeasureScope.kt:121)
                                                                                                    	at androidx.compose.foundation.lazy.grid.LazyGridMeasuredItemProvider.getAndMeasure-3p2s80s(LazyGridMeasuredItemProvider.kt:45)
                                                                                                    	at androidx.compose.foundation.lazy.grid.LazyGridMeasuredLineProvider.getAndMeasure(LazyGridMeasuredLineProvider.kt:80)
                                                                                                    	at androidx.compose.foundation.lazy.grid.LazyGridMeasureKt.measureLazyGrid-ZRKPzZ8(LazyGridMeasure.kt:151)
                                                                                                    	at androidx.compose.foundation.lazy.grid.LazyGridKt$rememberLazyGridMeasurePolicy$1$1.invoke-0kLqBqw(LazyGrid.kt:342)
                                                                                                    	at androidx.compose.foundation.lazy.grid.LazyGridKt$rememberLazyGridMeasurePolicy$1$1.invoke(LazyGrid.kt:183)
                                                                                                    	at androidx.compose.foundation.lazy.layout.LazyLayoutKt$LazyLayout$3$2$1.invoke-0kLqBqw(LazyLayout.kt:87)
                                                                                                    	at androidx.compose.foundation.lazy.layout.LazyLayoutKt$LazyLayout$3$2$1.invoke(LazyLayout.kt:80)
                                                                                                    	at androidx.compose.ui.layout.LayoutNodeSubcompositionsState$createMeasurePolicy$1.measure-3p2s80s(SubcomposeLayout.kt:866)
                                                                                                    	at androidx.compose.ui.node.InnerNodeCoordinator.measure-BRTryo0(InnerNodeCoordinator.kt:126)
                                                                                                    	at androidx.compose.foundation.AndroidOverscrollKt$StretchOverscrollNonClippingLayer$2.invoke-3p2s80s(AndroidOverscroll.kt:578)
                                                                                                    	at androidx.compose.foundation.AndroidOverscrollKt$StretchOverscrollNonClippingLayer$2.invoke(AndroidOverscroll.kt:577)
                                                                                                    	at androidx.compose.ui.layout.LayoutModifierImpl.measure-3p2s80s(LayoutModifier.kt:291)
                                                                                                    	at androidx.compose.ui.node.LayoutModifierNodeCoordinator.measure-BRTryo0(LayoutModifierNodeCoordinator.kt:116)
                                                                                                    	at androidx.compose.foundation.AndroidOverscrollKt$StretchOverscrollNonClippingLayer$1.invoke-3p2s80s(AndroidOverscroll.kt:562)
                                                                                                    	at androidx.compose.foundation.AndroidOverscrollKt$StretchOverscrollNonClippingLayer$1.invoke(AndroidOverscroll.kt:561)
                                                                                                    	at androidx.compose.ui.layout.LayoutModifierImpl.measure-3p2s80s(LayoutModifier.kt:291)
                                                                                                    	at androidx.compose.ui.node.LayoutModifierNodeCoordinator.measure-BRTryo0(LayoutModifierNodeCoordinator.kt:116)
                                                                                                    	at androidx.compose.ui.graphics.SimpleGraphicsLayerModifier.measure-3p2s80s(GraphicsLayerModifier.kt:646)
                                                                                                    	at androidx.compose.ui.node.LayoutModifierNodeCoordinator.measure-BRTryo0(LayoutModifierNodeCoordinator.kt:116)
                                                                                                    	at androidx.compose.foundation.layout.FillNode.measure-3p2s80s(Size.kt:698)
                                                                                                    	at androidx.compose.ui.node.LayoutModifierNodeCoordinator.measure-BRTryo0(LayoutModifierNodeCoordinator.kt:116)
                                                                                                    	at androidx.compose.foundation.layout.FillNode.measure-3p2s80s(Size.kt:698)
                                                                                                    	at androidx.compose.ui.node.LayoutModifierNodeCoordinator.measure-BRTryo0(LayoutModifierNodeCoordinator.kt:116)
                                                                                                    	at androidx.compose.ui.node.LayoutNodeLayoutDelegate$performMeasure$2.invoke(LayoutNodeLayoutDelegate.kt:1499)
                                                                                                    	at androidx.compose.ui.node.LayoutNodeLayoutDelegate$performMeasure$2.invoke(LayoutNodeLayoutDelegate.kt:1495)
                                                                                                    	at androidx.compose.runtime.snapshots.Snapshot$Companion.observe(Snapshot.kt:2299)
                                                                                                    	at androidx.compose.runtime.snapshots.SnapshotStateObserver$ObservedScopeMap.observe(SnapshotStateObserver.kt:467)
                                                                                                    	at androidx.compose.runtime.snapshots.SnapshotStateObserver.observeReads(SnapshotStateObserver.kt:230)
                                                                                                    	at androidx.compose.ui.node.OwnerSnapshotObserver.observeReads$ui_release(OwnerSnapshotObserver.kt:133)
                                                                                                    	at androidx.compose.ui.node.OwnerSnapshotObserver.observeMeasureSnapshotReads$ui_release(OwnerSnapshotObserver.kt:113)
                                                                                                    	at androidx.compose.ui.node.LayoutNodeLayoutDelegate.performMeasure-BRTryo0(LayoutNodeLayoutDelegate.kt:1495)
2025-12-16 13:58:42.168 28462-28462 AndroidRuntime          pid-28462                            E  FATAL EXCEPTION: main (Fix with AI)
                                                                                                    Process: com.aks_labs.pixelflow, PID: 28462
                                                                                                    java.lang.IllegalArgumentException: Navigation destination that matches request NavDeepLinkRequest{ uri=android-app://androidx.navigation/FavouritesGridView } cannot be found in the navigation graph NavGraph(0x0) startDestination={Destination(0x78d845ec) route=home}
                                                                                                    	at androidx.navigation.NavController.navigate(NavController.kt:1664)
                                                                                                    	at androidx.navigation.NavController.navigate(NavController.kt:1984)
                                                                                                    	at androidx.navigation.NavController.navigate$default(NavController.kt:1979)
                                                                                                    	at com.aks_labs.pixelflow.ui.screens.FolderScreenKt$FolderScreen$3$3$2$2.invoke(FolderScreen.kt:379)
                                                                                                    	at com.aks_labs.pixelflow.ui.screens.FolderScreenKt$FolderScreen$3$3$2$2.invoke(FolderScreen.kt:377)
                                                                                                    	at com.aks_labs.pixelflow.ui.screens.FolderScreenKt$CategoryList$1$1$1.invoke(FolderScreen.kt:579)
                                                                                                    	at com.aks_labs.pixelflow.ui.screens.FolderScreenKt$CategoryList$1$1$1.invoke(FolderScreen.kt:578)
                                                                                                    	at androidx.compose.foundation.ClickablePointerInputNode$pointerInput$3.invoke-k-4lQ0M(Clickable.kt:895)
                                                                                                    	at androidx.compose.foundation.ClickablePointerInputNode$pointerInput$3.invoke(Clickable.kt:889)
                                                                                                    	at androidx.compose.foundation.gestures.TapGestureDetectorKt$detectTapAndPress$2$1.invokeSuspend(TapGestureDetector.kt:255)
                                                                                                    	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
                                                                                                    	at kotlinx.coroutines.DispatchedTaskKt.resume(DispatchedTask.kt:178)
                                                                                                    	at kotlinx.coroutines.DispatchedTaskKt.dispatch(DispatchedTask.kt:166)
                                                                                                    	at kotlinx.coroutines.CancellableContinuationImpl.dispatchResume(CancellableContinuationImpl.kt:397)
                                                                                                    	at kotlinx.coroutines.CancellableContinuationImpl.resumeImpl(CancellableContinuationImpl.kt:431)
                                                                                                    	at kotlinx.coroutines.CancellableContinuationImpl.resumeImpl$default(CancellableContinuationImpl.kt:420)
                                                                                                    	at kotlinx.coroutines.CancellableContinuationImpl.resumeWith(CancellableContinuationImpl.kt:328)
                                                                                                    	at androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl$PointerEventHandlerCoroutine.offerPointerEvent(SuspendingPointerInputFilter.kt:665)
                                                                                                    	at androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl.dispatchPointerEvent(SuspendingPointerInputFilter.kt:544)
                                                                                                    	at androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl.onPointerEvent-H0pRuoY(SuspendingPointerInputFilter.kt:566)
                                                                                                    	at androidx.compose.foundation.AbstractClickablePointerInputNode.onPointerEvent-H0pRuoY(Clickable.kt:855)
                                                                                                    	at androidx.compose.foundation.AbstractClickableNode.onPointerEvent-H0pRuoY(Clickable.kt:703)
                                                                                                    	at androidx.compose.ui.input.pointer.Node.dispatchMainEventPass(HitPathTracker.kt:317)
                                                                                                    	at androidx.compose.ui.input.pointer.Node.dispatchMainEventPass(HitPathTracker.kt:303)
                                                                                                    	at androidx.compose.ui.input.pointer.Node.dispatchMainEventPass(HitPathTracker.kt:303)
                                                                                                    	at androidx.compose.ui.input.pointer.Node.dispatchMainEventPass(HitPathTracker.kt:303)
                                                                                                    	at androidx.compose.ui.input.pointer.Node.dispatchMainEventPass(HitPathTracker.kt:303)
                                                                                                    	at androidx.compose.ui.input.pointer.Node.dispatchMainEventPass(HitPathTracker.kt:303)
                                                                                                    	at androidx.compose.ui.input.pointer.Node.dispatchMainEventPass(HitPathTracker.kt:303)
                                                                                                    	at androidx.compose.ui.input.pointer.Node.dispatchMainEventPass(HitPathTracker.kt:303)
                                                                                                    	at androidx.compose.ui.input.pointer.NodeParent.dispatchMainEventPass(HitPathTracker.kt:183)
                                                                                                    	at androidx.compose.ui.input.pointer.HitPathTracker.dispatchChanges(HitPathTracker.kt:102)
                                                                                                    	at androidx.compose.ui.input.pointer.PointerInputEventProcessor.process-BIzXfog(PointerInputEventProcessor.kt:96)
                                                                                                    	at androidx.compose.ui.platform.AndroidComposeView.sendMotionEvent-8iAsVTc(AndroidComposeView.android.kt:1446)
                                                                                                    	at androidx.compose.ui.platform.AndroidComposeView.handleMotionEvent-8iAsVTc(AndroidComposeView.android.kt:1398)
                                                                                                    	at androidx.compose.ui.platform.AndroidComposeView.dispatchTouchEvent(AndroidComposeView.android.kt:1338)
                                                                                                    	at android.view.ViewGroup.dispatchTransformedTouchEvent(ViewGroup.java:3144)
2025-12-16 20:47:09.626 31247-31247 AndroidRuntime          pid-31247                            E  FATAL EXCEPTION: main (Fix with AI)
                                                                                                    Process: com.aks_labs.pixelflow, PID: 31247
                                                                                                    java.lang.IllegalStateException: ViewTreeLifecycleOwner not found from androidx.compose.ui.platform.ComposeView{9088670 V.E...... ......I. 0,0-0,0}
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposer_androidKt.createLifecycleAwareWindowRecomposer(WindowRecomposer.android.kt:352)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposer_androidKt.createLifecycleAwareWindowRecomposer$default(WindowRecomposer.android.kt:325)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposerFactory$Companion$LifecycleAware$1.createRecomposer(WindowRecomposer.android.kt:168)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposerPolicy.createAndInstallWindowRecomposer$ui_release(WindowRecomposer.android.kt:224)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposer_androidKt.getWindowRecomposer(WindowRecomposer.android.kt:300)
                                                                                                    	at androidx.compose.ui.platform.AbstractComposeView.resolveParentCompositionContext(ComposeView.android.kt:244)
                                                                                                    	at androidx.compose.ui.platform.AbstractComposeView.ensureCompositionCreated(ComposeView.android.kt:251)
                                                                                                    	at androidx.compose.ui.platform.AbstractComposeView.onAttachedToWindow(ComposeView.android.kt:283)
                                                                                                    	at android.view.View.dispatchAttachedToWindow(View.java:23071)
                                                                                                    	at android.view.ViewGroup.dispatchAttachedToWindow(ViewGroup.java:3518)
                                                                                                    	at android.view.ViewRootImpl.performTraversals(ViewRootImpl.java:3571)
                                                                                                    	at android.view.ViewRootImpl.doTraversal(ViewRootImpl.java:3029)
                                                                                                    	at android.view.ViewRootImpl$TraversalRunnable.run(ViewRootImpl.java:10540)
                                                                                                    	at android.view.Choreographer$CallbackRecord.run(Choreographer.java:1566)
                                                                                                    	at android.view.Choreographer$CallbackRecord.run(Choreographer.java:1575)
                                                                                                    	at android.view.Choreographer.doCallbacks(Choreographer.java:1175)
                                                                                                    	at android.view.Choreographer.doFrame(Choreographer.java:1104)
                                                                                                    	at android.view.Choreographer$FrameDisplayEventReceiver.run(Choreographer.java:1549)
                                                                                                    	at android.os.Handler.handleCallback(Handler.java:991)
                                                                                                    	at android.os.Handler.dispatchMessage(Handler.java:102)
                                                                                                    	at android.os.Looper.loopOnce(Looper.java:232)
                                                                                                    	at android.os.Looper.loop(Looper.java:317)
                                                                                                    	at android.app.ActivityThread.main(ActivityThread.java:8929)
                                                                                                    	at java.lang.reflect.Method.invoke(Native Method)
                                                                                                    	at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:591)
                                                                                                    	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:911)
2025-12-16 20:47:47.106 31665-31665 AndroidRuntime          pid-31665                            E  FATAL EXCEPTION: main (Fix with AI)
                                                                                                    Process: com.aks_labs.pixelflow, PID: 31665
                                                                                                    java.lang.IllegalStateException: ViewTreeLifecycleOwner not found from androidx.compose.ui.platform.ComposeView{2859479 V.E...... ......I. 0,0-0,0}
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposer_androidKt.createLifecycleAwareWindowRecomposer(WindowRecomposer.android.kt:352)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposer_androidKt.createLifecycleAwareWindowRecomposer$default(WindowRecomposer.android.kt:325)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposerFactory$Companion$LifecycleAware$1.createRecomposer(WindowRecomposer.android.kt:168)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposerPolicy.createAndInstallWindowRecomposer$ui_release(WindowRecomposer.android.kt:224)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposer_androidKt.getWindowRecomposer(WindowRecomposer.android.kt:300)
                                                                                                    	at androidx.compose.ui.platform.AbstractComposeView.resolveParentCompositionContext(ComposeView.android.kt:244)
                                                                                                    	at androidx.compose.ui.platform.AbstractComposeView.ensureCompositionCreated(ComposeView.android.kt:251)
                                                                                                    	at androidx.compose.ui.platform.AbstractComposeView.onAttachedToWindow(ComposeView.android.kt:283)
                                                                                                    	at android.view.View.dispatchAttachedToWindow(View.java:23071)
                                                                                                    	at android.view.ViewGroup.dispatchAttachedToWindow(ViewGroup.java:3518)
                                                                                                    	at android.view.ViewRootImpl.performTraversals(ViewRootImpl.java:3571)
                                                                                                    	at android.view.ViewRootImpl.doTraversal(ViewRootImpl.java:3029)
                                                                                                    	at android.view.ViewRootImpl$TraversalRunnable.run(ViewRootImpl.java:10540)
                                                                                                    	at android.view.Choreographer$CallbackRecord.run(Choreographer.java:1566)
                                                                                                    	at android.view.Choreographer$CallbackRecord.run(Choreographer.java:1575)
                                                                                                    	at android.view.Choreographer.doCallbacks(Choreographer.java:1175)
                                                                                                    	at android.view.Choreographer.doFrame(Choreographer.java:1104)
                                                                                                    	at android.view.Choreographer$FrameDisplayEventReceiver.run(Choreographer.java:1549)
                                                                                                    	at android.os.Handler.handleCallback(Handler.java:991)
                                                                                                    	at android.os.Handler.dispatchMessage(Handler.java:102)
                                                                                                    	at android.os.Looper.loopOnce(Looper.java:232)
                                                                                                    	at android.os.Looper.loop(Looper.java:317)
                                                                                                    	at android.app.ActivityThread.main(ActivityThread.java:8929)
                                                                                                    	at java.lang.reflect.Method.invoke(Native Method)
                                                                                                    	at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:591)
                                                                                                    	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:911)
2025-12-16 20:52:41.955  1047-1047  AndroidRuntime          pid-1047                             E  FATAL EXCEPTION: main (Fix with AI)
                                                                                                    Process: com.aks_labs.pixelflow, PID: 1047
                                                                                                    java.lang.IllegalStateException: ViewTreeLifecycleOwner not found from com.aks_labs.pixelflow.ui.components.compose.ServiceComposeViewImpl{e88c61f V.E...... ......I. 0,0-0,0}
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposer_androidKt.createLifecycleAwareWindowRecomposer(WindowRecomposer.android.kt:352)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposer_androidKt.createLifecycleAwareWindowRecomposer$default(WindowRecomposer.android.kt:325)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposerFactory$Companion$LifecycleAware$1.createRecomposer(WindowRecomposer.android.kt:168)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposerPolicy.createAndInstallWindowRecomposer$ui_release(WindowRecomposer.android.kt:224)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposer_androidKt.getWindowRecomposer(WindowRecomposer.android.kt:300)
                                                                                                    	at androidx.compose.ui.platform.AbstractComposeView.resolveParentCompositionContext(ComposeView.android.kt:244)
                                                                                                    	at androidx.compose.ui.platform.AbstractComposeView.ensureCompositionCreated(ComposeView.android.kt:251)
                                                                                                    	at androidx.compose.ui.platform.AbstractComposeView.onAttachedToWindow(ComposeView.android.kt:283)
                                                                                                    	at android.view.View.dispatchAttachedToWindow(View.java:23071)
                                                                                                    	at android.view.ViewGroup.dispatchAttachedToWindow(ViewGroup.java:3518)
                                                                                                    	at android.view.ViewRootImpl.performTraversals(ViewRootImpl.java:3571)
                                                                                                    	at android.view.ViewRootImpl.doTraversal(ViewRootImpl.java:3029)
                                                                                                    	at android.view.ViewRootImpl$TraversalRunnable.run(ViewRootImpl.java:10540)
                                                                                                    	at android.view.Choreographer$CallbackRecord.run(Choreographer.java:1566)
                                                                                                    	at android.view.Choreographer$CallbackRecord.run(Choreographer.java:1575)
                                                                                                    	at android.view.Choreographer.doCallbacks(Choreographer.java:1175)
                                                                                                    	at android.view.Choreographer.doFrame(Choreographer.java:1104)
                                                                                                    	at android.view.Choreographer$FrameDisplayEventReceiver.run(Choreographer.java:1549)
                                                                                                    	at android.os.Handler.handleCallback(Handler.java:991)
                                                                                                    	at android.os.Handler.dispatchMessage(Handler.java:102)
                                                                                                    	at android.os.Looper.loopOnce(Looper.java:232)
                                                                                                    	at android.os.Looper.loop(Looper.java:317)
                                                                                                    	at android.app.ActivityThread.main(ActivityThread.java:8929)
                                                                                                    	at java.lang.reflect.Method.invoke(Native Method)
                                                                                                    	at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:591)
                                                                                                    	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:911)
2025-12-16 20:52:50.984  1567-1567  AndroidRuntime          pid-1567                             E  FATAL EXCEPTION: main (Fix with AI)
                                                                                                    Process: com.aks_labs.pixelflow, PID: 1567
                                                                                                    java.lang.IllegalStateException: ViewTreeLifecycleOwner not found from com.aks_labs.pixelflow.ui.components.compose.ServiceComposeViewImpl{21a4ad9 V.E...... ......I. 0,0-0,0}
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposer_androidKt.createLifecycleAwareWindowRecomposer(WindowRecomposer.android.kt:352)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposer_androidKt.createLifecycleAwareWindowRecomposer$default(WindowRecomposer.android.kt:325)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposerFactory$Companion$LifecycleAware$1.createRecomposer(WindowRecomposer.android.kt:168)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposerPolicy.createAndInstallWindowRecomposer$ui_release(WindowRecomposer.android.kt:224)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposer_androidKt.getWindowRecomposer(WindowRecomposer.android.kt:300)
                                                                                                    	at androidx.compose.ui.platform.AbstractComposeView.resolveParentCompositionContext(ComposeView.android.kt:244)
                                                                                                    	at androidx.compose.ui.platform.AbstractComposeView.ensureCompositionCreated(ComposeView.android.kt:251)
                                                                                                    	at androidx.compose.ui.platform.AbstractComposeView.onAttachedToWindow(ComposeView.android.kt:283)
                                                                                                    	at android.view.View.dispatchAttachedToWindow(View.java:23071)
                                                                                                    	at android.view.ViewGroup.dispatchAttachedToWindow(ViewGroup.java:3518)
                                                                                                    	at android.view.ViewRootImpl.performTraversals(ViewRootImpl.java:3571)
                                                                                                    	at android.view.ViewRootImpl.doTraversal(ViewRootImpl.java:3029)
                                                                                                    	at android.view.ViewRootImpl$TraversalRunnable.run(ViewRootImpl.java:10540)
                                                                                                    	at android.view.Choreographer$CallbackRecord.run(Choreographer.java:1566)
                                                                                                    	at android.view.Choreographer$CallbackRecord.run(Choreographer.java:1575)
                                                                                                    	at android.view.Choreographer.doCallbacks(Choreographer.java:1175)
                                                                                                    	at android.view.Choreographer.doFrame(Choreographer.java:1104)
                                                                                                    	at android.view.Choreographer$FrameDisplayEventReceiver.run(Choreographer.java:1549)
                                                                                                    	at android.os.Handler.handleCallback(Handler.java:991)
                                                                                                    	at android.os.Handler.dispatchMessage(Handler.java:102)
                                                                                                    	at android.os.Looper.loopOnce(Looper.java:232)
                                                                                                    	at android.os.Looper.loop(Looper.java:317)
                                                                                                    	at android.app.ActivityThread.main(ActivityThread.java:8929)
                                                                                                    	at java.lang.reflect.Method.invoke(Native Method)
                                                                                                    	at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:591)
                                                                                                    	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:911)
2025-12-16 20:57:16.872  3098-3098  AndroidRuntime          pid-3098                             E  FATAL EXCEPTION: main (Fix with AI)
                                                                                                    Process: com.aks_labs.pixelflow, PID: 3098
                                                                                                    java.lang.IllegalStateException: ViewTreeLifecycleOwner not found from com.aks_labs.pixelflow.ui.components.compose.ServiceComposeViewImpl{4b345be V.E...... ......I. 0,0-0,0}
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposer_androidKt.createLifecycleAwareWindowRecomposer(WindowRecomposer.android.kt:352)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposer_androidKt.createLifecycleAwareWindowRecomposer$default(WindowRecomposer.android.kt:325)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposerFactory$Companion$LifecycleAware$1.createRecomposer(WindowRecomposer.android.kt:168)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposerPolicy.createAndInstallWindowRecomposer$ui_release(WindowRecomposer.android.kt:224)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposer_androidKt.getWindowRecomposer(WindowRecomposer.android.kt:300)
                                                                                                    	at androidx.compose.ui.platform.AbstractComposeView.resolveParentCompositionContext(ComposeView.android.kt:244)
                                                                                                    	at androidx.compose.ui.platform.AbstractComposeView.ensureCompositionCreated(ComposeView.android.kt:251)
                                                                                                    	at androidx.compose.ui.platform.AbstractComposeView.onAttachedToWindow(ComposeView.android.kt:283)
                                                                                                    	at android.view.View.dispatchAttachedToWindow(View.java:23071)
                                                                                                    	at android.view.ViewGroup.dispatchAttachedToWindow(ViewGroup.java:3518)
                                                                                                    	at android.view.ViewRootImpl.performTraversals(ViewRootImpl.java:3571)
                                                                                                    	at android.view.ViewRootImpl.doTraversal(ViewRootImpl.java:3029)
                                                                                                    	at android.view.ViewRootImpl$TraversalRunnable.run(ViewRootImpl.java:10540)
                                                                                                    	at android.view.Choreographer$CallbackRecord.run(Choreographer.java:1566)
                                                                                                    	at android.view.Choreographer$CallbackRecord.run(Choreographer.java:1575)
                                                                                                    	at android.view.Choreographer.doCallbacks(Choreographer.java:1175)
                                                                                                    	at android.view.Choreographer.doFrame(Choreographer.java:1104)
                                                                                                    	at android.view.Choreographer$FrameDisplayEventReceiver.run(Choreographer.java:1549)
                                                                                                    	at android.os.Handler.handleCallback(Handler.java:991)
                                                                                                    	at android.os.Handler.dispatchMessage(Handler.java:102)
                                                                                                    	at android.os.Looper.loopOnce(Looper.java:232)
                                                                                                    	at android.os.Looper.loop(Looper.java:317)
                                                                                                    	at android.app.ActivityThread.main(ActivityThread.java:8929)
                                                                                                    	at java.lang.reflect.Method.invoke(Native Method)
                                                                                                    	at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:591)
                                                                                                    	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:911)
2025-12-16 20:57:26.872  3224-3224  AndroidRuntime          pid-3224                             E  FATAL EXCEPTION: main (Fix with AI)
                                                                                                    Process: com.aks_labs.pixelflow, PID: 3224
                                                                                                    java.lang.IllegalStateException: ViewTreeLifecycleOwner not found from com.aks_labs.pixelflow.ui.components.compose.ServiceComposeViewImpl{1975823 V.E...... ......I. 0,0-0,0}
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposer_androidKt.createLifecycleAwareWindowRecomposer(WindowRecomposer.android.kt:352)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposer_androidKt.createLifecycleAwareWindowRecomposer$default(WindowRecomposer.android.kt:325)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposerFactory$Companion$LifecycleAware$1.createRecomposer(WindowRecomposer.android.kt:168)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposerPolicy.createAndInstallWindowRecomposer$ui_release(WindowRecomposer.android.kt:224)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposer_androidKt.getWindowRecomposer(WindowRecomposer.android.kt:300)
                                                                                                    	at androidx.compose.ui.platform.AbstractComposeView.resolveParentCompositionContext(ComposeView.android.kt:244)
                                                                                                    	at androidx.compose.ui.platform.AbstractComposeView.ensureCompositionCreated(ComposeView.android.kt:251)
                                                                                                    	at androidx.compose.ui.platform.AbstractComposeView.onAttachedToWindow(ComposeView.android.kt:283)
                                                                                                    	at android.view.View.dispatchAttachedToWindow(View.java:23071)
                                                                                                    	at android.view.ViewGroup.dispatchAttachedToWindow(ViewGroup.java:3518)
                                                                                                    	at android.view.ViewRootImpl.performTraversals(ViewRootImpl.java:3571)
                                                                                                    	at android.view.ViewRootImpl.doTraversal(ViewRootImpl.java:3029)
                                                                                                    	at android.view.ViewRootImpl$TraversalRunnable.run(ViewRootImpl.java:10540)
                                                                                                    	at android.view.Choreographer$CallbackRecord.run(Choreographer.java:1566)
                                                                                                    	at android.view.Choreographer$CallbackRecord.run(Choreographer.java:1575)
                                                                                                    	at android.view.Choreographer.doCallbacks(Choreographer.java:1175)
                                                                                                    	at android.view.Choreographer.doFrame(Choreographer.java:1104)
                                                                                                    	at android.view.Choreographer$FrameDisplayEventReceiver.run(Choreographer.java:1549)
                                                                                                    	at android.os.Handler.handleCallback(Handler.java:991)
                                                                                                    	at android.os.Handler.dispatchMessage(Handler.java:102)
                                                                                                    	at android.os.Looper.loopOnce(Looper.java:232)
                                                                                                    	at android.os.Looper.loop(Looper.java:317)
                                                                                                    	at android.app.ActivityThread.main(ActivityThread.java:8929)
                                                                                                    	at java.lang.reflect.Method.invoke(Native Method)
                                                                                                    	at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:591)
                                                                                                    	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:911)
2025-12-17 15:17:30.370 26386-26386 AndroidRuntime          pid-26386                            E  FATAL EXCEPTION: main (Fix with AI)
                                                                                                    Process: com.aks_labs.pixelflow, PID: 26386
                                                                                                    java.lang.IllegalStateException: ViewTreeLifecycleOwner not found from com.aks_labs.pixelflow.ui.components.compose.ServiceComposeViewImpl{9004e96 V.E...... ......I. 0,0-0,0}
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposer_androidKt.createLifecycleAwareWindowRecomposer(WindowRecomposer.android.kt:352)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposer_androidKt.createLifecycleAwareWindowRecomposer$default(WindowRecomposer.android.kt:325)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposerFactory$Companion$LifecycleAware$1.createRecomposer(WindowRecomposer.android.kt:168)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposerPolicy.createAndInstallWindowRecomposer$ui_release(WindowRecomposer.android.kt:224)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposer_androidKt.getWindowRecomposer(WindowRecomposer.android.kt:300)
                                                                                                    	at androidx.compose.ui.platform.AbstractComposeView.resolveParentCompositionContext(ComposeView.android.kt:244)
                                                                                                    	at androidx.compose.ui.platform.AbstractComposeView.ensureCompositionCreated(ComposeView.android.kt:251)
                                                                                                    	at androidx.compose.ui.platform.AbstractComposeView.onAttachedToWindow(ComposeView.android.kt:283)
                                                                                                    	at android.view.View.dispatchAttachedToWindow(View.java:23071)
                                                                                                    	at android.view.ViewGroup.dispatchAttachedToWindow(ViewGroup.java:3518)
                                                                                                    	at android.view.ViewRootImpl.performTraversals(ViewRootImpl.java:3571)
                                                                                                    	at android.view.ViewRootImpl.doTraversal(ViewRootImpl.java:3029)
                                                                                                    	at android.view.ViewRootImpl$TraversalRunnable.run(ViewRootImpl.java:10540)
                                                                                                    	at android.view.Choreographer$CallbackRecord.run(Choreographer.java:1566)
                                                                                                    	at android.view.Choreographer$CallbackRecord.run(Choreographer.java:1575)
                                                                                                    	at android.view.Choreographer.doCallbacks(Choreographer.java:1175)
                                                                                                    	at android.view.Choreographer.doFrame(Choreographer.java:1104)
                                                                                                    	at android.view.Choreographer$FrameDisplayEventReceiver.run(Choreographer.java:1549)
                                                                                                    	at android.os.Handler.handleCallback(Handler.java:991)
                                                                                                    	at android.os.Handler.dispatchMessage(Handler.java:102)
                                                                                                    	at android.os.Looper.loopOnce(Looper.java:232)
                                                                                                    	at android.os.Looper.loop(Looper.java:317)
                                                                                                    	at android.app.ActivityThread.main(ActivityThread.java:8929)
                                                                                                    	at java.lang.reflect.Method.invoke(Native Method)
                                                                                                    	at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:591)
                                                                                                    	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:911)
2025-12-17 15:20:22.237 28192-28192 AndroidRuntime          pid-28192                            E  FATAL EXCEPTION: main (Fix with AI)
                                                                                                    Process: com.aks_labs.pixelflow, PID: 28192
                                                                                                    java.lang.IllegalStateException: ViewTreeLifecycleOwner not found from com.aks_labs.pixelflow.ui.components.compose.ServiceComposeViewImpl{d952615 V.E...... ......I. 0,0-0,0}
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposer_androidKt.createLifecycleAwareWindowRecomposer(WindowRecomposer.android.kt:352)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposer_androidKt.createLifecycleAwareWindowRecomposer$default(WindowRecomposer.android.kt:325)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposerFactory$Companion$LifecycleAware$1.createRecomposer(WindowRecomposer.android.kt:168)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposerPolicy.createAndInstallWindowRecomposer$ui_release(WindowRecomposer.android.kt:224)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposer_androidKt.getWindowRecomposer(WindowRecomposer.android.kt:300)
                                                                                                    	at androidx.compose.ui.platform.AbstractComposeView.resolveParentCompositionContext(ComposeView.android.kt:244)
                                                                                                    	at androidx.compose.ui.platform.AbstractComposeView.ensureCompositionCreated(ComposeView.android.kt:251)
                                                                                                    	at androidx.compose.ui.platform.AbstractComposeView.onAttachedToWindow(ComposeView.android.kt:283)
                                                                                                    	at android.view.View.dispatchAttachedToWindow(View.java:23071)
                                                                                                    	at android.view.ViewGroup.dispatchAttachedToWindow(ViewGroup.java:3518)
                                                                                                    	at android.view.ViewRootImpl.performTraversals(ViewRootImpl.java:3571)
                                                                                                    	at android.view.ViewRootImpl.doTraversal(ViewRootImpl.java:3029)
                                                                                                    	at android.view.ViewRootImpl$TraversalRunnable.run(ViewRootImpl.java:10540)
                                                                                                    	at android.view.Choreographer$CallbackRecord.run(Choreographer.java:1566)
                                                                                                    	at android.view.Choreographer$CallbackRecord.run(Choreographer.java:1575)
                                                                                                    	at android.view.Choreographer.doCallbacks(Choreographer.java:1175)
                                                                                                    	at android.view.Choreographer.doFrame(Choreographer.java:1104)
                                                                                                    	at android.view.Choreographer$FrameDisplayEventReceiver.run(Choreographer.java:1549)
                                                                                                    	at android.os.Handler.handleCallback(Handler.java:991)
                                                                                                    	at android.os.Handler.dispatchMessage(Handler.java:102)
                                                                                                    	at android.os.Looper.loopOnce(Looper.java:232)
                                                                                                    	at android.os.Looper.loop(Looper.java:317)
                                                                                                    	at android.app.ActivityThread.main(ActivityThread.java:8929)
                                                                                                    	at java.lang.reflect.Method.invoke(Native Method)
                                                                                                    	at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:591)
                                                                                                    	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:911)
--------- beginning of system
2025-12-17 15:20:33.049 28405-28405 AndroidRuntime          pid-28405                            E  FATAL EXCEPTION: main (Fix with AI)
                                                                                                    Process: com.aks_labs.pixelflow, PID: 28405
                                                                                                    java.lang.IllegalStateException: ViewTreeLifecycleOwner not found from com.aks_labs.pixelflow.ui.components.compose.ServiceComposeViewImpl{85d6120 V.E...... ......I. 0,0-0,0}
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposer_androidKt.createLifecycleAwareWindowRecomposer(WindowRecomposer.android.kt:352)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposer_androidKt.createLifecycleAwareWindowRecomposer$default(WindowRecomposer.android.kt:325)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposerFactory$Companion$LifecycleAware$1.createRecomposer(WindowRecomposer.android.kt:168)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposerPolicy.createAndInstallWindowRecomposer$ui_release(WindowRecomposer.android.kt:224)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposer_androidKt.getWindowRecomposer(WindowRecomposer.android.kt:300)
                                                                                                    	at androidx.compose.ui.platform.AbstractComposeView.resolveParentCompositionContext(ComposeView.android.kt:244)
                                                                                                    	at androidx.compose.ui.platform.AbstractComposeView.ensureCompositionCreated(ComposeView.android.kt:251)
                                                                                                    	at androidx.compose.ui.platform.AbstractComposeView.onAttachedToWindow(ComposeView.android.kt:283)
                                                                                                    	at android.view.View.dispatchAttachedToWindow(View.java:23071)
                                                                                                    	at android.view.ViewGroup.dispatchAttachedToWindow(ViewGroup.java:3518)
                                                                                                    	at android.view.ViewRootImpl.performTraversals(ViewRootImpl.java:3571)
                                                                                                    	at android.view.ViewRootImpl.doTraversal(ViewRootImpl.java:3029)
                                                                                                    	at android.view.ViewRootImpl$TraversalRunnable.run(ViewRootImpl.java:10540)
                                                                                                    	at android.view.Choreographer$CallbackRecord.run(Choreographer.java:1566)
                                                                                                    	at android.view.Choreographer$CallbackRecord.run(Choreographer.java:1575)
                                                                                                    	at android.view.Choreographer.doCallbacks(Choreographer.java:1175)
                                                                                                    	at android.view.Choreographer.doFrame(Choreographer.java:1104)
                                                                                                    	at android.view.Choreographer$FrameDisplayEventReceiver.run(Choreographer.java:1549)
                                                                                                    	at android.os.Handler.handleCallback(Handler.java:991)
                                                                                                    	at android.os.Handler.dispatchMessage(Handler.java:102)
                                                                                                    	at android.os.Looper.loopOnce(Looper.java:232)
                                                                                                    	at android.os.Looper.loop(Looper.java:317)
                                                                                                    	at android.app.ActivityThread.main(ActivityThread.java:8929)
                                                                                                    	at java.lang.reflect.Method.invoke(Native Method)
                                                                                                    	at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:591)
                                                                                                    	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:911)
2025-12-17 15:53:04.491  1985-2125  VerityUtils             system_server                        E  Failed to check whether fs-verity is enabled, errno 38: /data/app/~~zMi879nNbGPaykwAkdsMvw==/com.aks_labs.pixelflow-phfPSfUd7JvJQUBNphCrKQ==/base.apk
2025-12-17 15:53:04.828  1985-2125  VerityUtils             system_server                        E  Failed to check whether fs-verity is enabled, errno 38: /data/app/~~zMi879nNbGPaykwAkdsMvw==/com.aks_labs.pixelflow-phfPSfUd7JvJQUBNphCrKQ==/base.apk
2025-12-17 15:53:18.678  5337-5337  AndroidRuntime          pid-5337                             E  FATAL EXCEPTION: main (Fix with AI)
                                                                                                    Process: com.aks_labs.pixelflow, PID: 5337
                                                                                                    java.lang.IllegalStateException: ViewTreeLifecycleOwner not found from com.aks_labs.pixelflow.ui.components.compose.ServiceComposeViewImpl{6d9c292 V.E...... ......I. 0,0-0,0}
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposer_androidKt.createLifecycleAwareWindowRecomposer(WindowRecomposer.android.kt:352)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposer_androidKt.createLifecycleAwareWindowRecomposer$default(WindowRecomposer.android.kt:325)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposerFactory$Companion$LifecycleAware$1.createRecomposer(WindowRecomposer.android.kt:168)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposerPolicy.createAndInstallWindowRecomposer$ui_release(WindowRecomposer.android.kt:224)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposer_androidKt.getWindowRecomposer(WindowRecomposer.android.kt:300)
                                                                                                    	at androidx.compose.ui.platform.AbstractComposeView.resolveParentCompositionContext(ComposeView.android.kt:244)
                                                                                                    	at androidx.compose.ui.platform.AbstractComposeView.ensureCompositionCreated(ComposeView.android.kt:251)
                                                                                                    	at androidx.compose.ui.platform.AbstractComposeView.onAttachedToWindow(ComposeView.android.kt:283)
                                                                                                    	at android.view.View.dispatchAttachedToWindow(View.java:23071)
                                                                                                    	at android.view.ViewGroup.dispatchAttachedToWindow(ViewGroup.java:3518)
                                                                                                    	at android.view.ViewRootImpl.performTraversals(ViewRootImpl.java:3571)
                                                                                                    	at android.view.ViewRootImpl.doTraversal(ViewRootImpl.java:3029)
                                                                                                    	at android.view.ViewRootImpl$TraversalRunnable.run(ViewRootImpl.java:10540)
                                                                                                    	at android.view.Choreographer$CallbackRecord.run(Choreographer.java:1566)
                                                                                                    	at android.view.Choreographer$CallbackRecord.run(Choreographer.java:1575)
                                                                                                    	at android.view.Choreographer.doCallbacks(Choreographer.java:1175)
                                                                                                    	at android.view.Choreographer.doFrame(Choreographer.java:1104)
                                                                                                    	at android.view.Choreographer$FrameDisplayEventReceiver.run(Choreographer.java:1549)
                                                                                                    	at android.os.Handler.handleCallback(Handler.java:991)
                                                                                                    	at android.os.Handler.dispatchMessage(Handler.java:102)
                                                                                                    	at android.os.Looper.loopOnce(Looper.java:232)
                                                                                                    	at android.os.Looper.loop(Looper.java:317)
                                                                                                    	at android.app.ActivityThread.main(ActivityThread.java:8929)
                                                                                                    	at java.lang.reflect.Method.invoke(Native Method)
                                                                                                    	at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:591)
                                                                                                    	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:911)
2025-12-17 15:57:33.211  6859-6859  AndroidRuntime          pid-6859                             E  FATAL EXCEPTION: main (Fix with AI)
                                                                                                    Process: com.aks_labs.pixelflow, PID: 6859
                                                                                                    java.lang.IllegalStateException: Composed into the View which doesn't propagateViewTreeSavedStateRegistryOwner!
                                                                                                    	at androidx.compose.ui.platform.AndroidComposeView.onAttachedToWindow(AndroidComposeView.android.kt:1254)
                                                                                                    	at android.view.View.dispatchAttachedToWindow(View.java:23071)
                                                                                                    	at android.view.ViewGroup.dispatchAttachedToWindow(ViewGroup.java:3518)
                                                                                                    	at android.view.ViewGroup.dispatchAttachedToWindow(ViewGroup.java:3525)
                                                                                                    	at android.view.ViewRootImpl.performTraversals(ViewRootImpl.java:3571)
                                                                                                    	at android.view.ViewRootImpl.doTraversal(ViewRootImpl.java:3029)
                                                                                                    	at android.view.ViewRootImpl$TraversalRunnable.run(ViewRootImpl.java:10540)
                                                                                                    	at android.view.Choreographer$CallbackRecord.run(Choreographer.java:1566)
                                                                                                    	at android.view.Choreographer$CallbackRecord.run(Choreographer.java:1575)
                                                                                                    	at android.view.Choreographer.doCallbacks(Choreographer.java:1175)
                                                                                                    	at android.view.Choreographer.doFrame(Choreographer.java:1104)
                                                                                                    	at android.view.Choreographer$FrameDisplayEventReceiver.run(Choreographer.java:1549)
                                                                                                    	at android.os.Handler.handleCallback(Handler.java:991)
                                                                                                    	at android.os.Handler.dispatchMessage(Handler.java:102)
                                                                                                    	at android.os.Looper.loopOnce(Looper.java:232)
                                                                                                    	at android.os.Looper.loop(Looper.java:317)
                                                                                                    	at android.app.ActivityThread.main(ActivityThread.java:8929)
                                                                                                    	at java.lang.reflect.Method.invoke(Native Method)
                                                                                                    	at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:591)
                                                                                                    	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:911)
2025-12-17 15:57:33.274  1985-2312  AppOps                  system_server                        E  Operation not started: uid=10804 pkg=com.aks_labs.pixelflow(null) op=VIBRATE
2025-12-17 15:57:41.318  6997-6997  AndroidRuntime          pid-6997                             E  FATAL EXCEPTION: main (Fix with AI)
                                                                                                    Process: com.aks_labs.pixelflow, PID: 6997
                                                                                                    java.lang.IllegalStateException: Composed into the View which doesn't propagateViewTreeSavedStateRegistryOwner!
                                                                                                    	at androidx.compose.ui.platform.AndroidComposeView.onAttachedToWindow(AndroidComposeView.android.kt:1254)
                                                                                                    	at android.view.View.dispatchAttachedToWindow(View.java:23071)
                                                                                                    	at android.view.ViewGroup.dispatchAttachedToWindow(ViewGroup.java:3518)
                                                                                                    	at android.view.ViewGroup.dispatchAttachedToWindow(ViewGroup.java:3525)
                                                                                                    	at android.view.ViewRootImpl.performTraversals(ViewRootImpl.java:3571)
                                                                                                    	at android.view.ViewRootImpl.doTraversal(ViewRootImpl.java:3029)
                                                                                                    	at android.view.ViewRootImpl$TraversalRunnable.run(ViewRootImpl.java:10540)
                                                                                                    	at android.view.Choreographer$CallbackRecord.run(Choreographer.java:1566)
                                                                                                    	at android.view.Choreographer$CallbackRecord.run(Choreographer.java:1575)
                                                                                                    	at android.view.Choreographer.doCallbacks(Choreographer.java:1175)
                                                                                                    	at android.view.Choreographer.doFrame(Choreographer.java:1104)
                                                                                                    	at android.view.Choreographer$FrameDisplayEventReceiver.run(Choreographer.java:1549)
                                                                                                    	at android.os.Handler.handleCallback(Handler.java:991)
                                                                                                    	at android.os.Handler.dispatchMessage(Handler.java:102)
                                                                                                    	at android.os.Looper.loopOnce(Looper.java:232)
                                                                                                    	at android.os.Looper.loop(Looper.java:317)
                                                                                                    	at android.app.ActivityThread.main(ActivityThread.java:8929)
                                                                                                    	at java.lang.reflect.Method.invoke(Native Method)
                                                                                                    	at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:591)
                                                                                                    	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:911)
2025-12-17 15:57:43.276  1985-11675 AppOps                  system_server                        E  Operation not started: uid=10804 pkg=com.aks_labs.pixelflow(null) op=SYSTEM_ALERT_WINDOW
2025-12-17 16:01:13.103  1985-2125  VerityUtils             system_server                        E  Failed to check whether fs-verity is enabled, errno 38: /data/app/~~XzmhQS5wj0aZKx-eLiMCfA==/com.aks_labs.pixelflow-PLMRaS-KAQs8Wktz0WInsA==/base.apk
2025-12-17 16:01:13.325  1985-2125  VerityUtils             system_server                        E  Failed to check whether fs-verity is enabled, errno 38: /data/app/~~XzmhQS5wj0aZKx-eLiMCfA==/com.aks_labs.pixelflow-PLMRaS-KAQs8Wktz0WInsA==/base.apk
2025-12-17 16:20:33.367  1985-2125  VerityUtils             system_server                        E  Failed to check whether fs-verity is enabled, errno 38: /data/app/~~ToyYmJNiBKZb0xu3ligtrg==/com.aks_labs.pixelflow-IWUpK34O8i8Zt7WjPGuW6w==/base.apk
2025-12-17 16:20:33.956  1985-2125  VerityUtils             system_server                        E  Failed to check whether fs-verity is enabled, errno 38: /data/app/~~ToyYmJNiBKZb0xu3ligtrg==/com.aks_labs.pixelflow-IWUpK34O8i8Zt7WjPGuW6w==/base.apk
2025-12-17 16:20:48.321 13778-13778 AndroidRuntime          pid-13778                            E  FATAL EXCEPTION: main (Fix with AI)
                                                                                                    Process: com.aks_labs.pixelflow, PID: 13778
                                                                                                    java.lang.IllegalStateException: ViewTreeLifecycleOwner not found from androidx.compose.ui.platform.ComposeView{2859479 V.E...... ......I. 0,0-0,0}
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposer_androidKt.createLifecycleAwareWindowRecomposer(WindowRecomposer.android.kt:352)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposer_androidKt.createLifecycleAwareWindowRecomposer$default(WindowRecomposer.android.kt:325)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposerFactory$Companion$LifecycleAware$1.createRecomposer(WindowRecomposer.android.kt:168)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposerPolicy.createAndInstallWindowRecomposer$ui_release(WindowRecomposer.android.kt:224)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposer_androidKt.getWindowRecomposer(WindowRecomposer.android.kt:300)
                                                                                                    	at androidx.compose.ui.platform.AbstractComposeView.resolveParentCompositionContext(ComposeView.android.kt:244)
                                                                                                    	at androidx.compose.ui.platform.AbstractComposeView.ensureCompositionCreated(ComposeView.android.kt:251)
                                                                                                    	at androidx.compose.ui.platform.AbstractComposeView.onAttachedToWindow(ComposeView.android.kt:283)
                                                                                                    	at android.view.View.dispatchAttachedToWindow(View.java:23071)
                                                                                                    	at android.view.ViewGroup.dispatchAttachedToWindow(ViewGroup.java:3518)
                                                                                                    	at android.view.ViewRootImpl.performTraversals(ViewRootImpl.java:3571)
                                                                                                    	at android.view.ViewRootImpl.doTraversal(ViewRootImpl.java:3029)
                                                                                                    	at android.view.ViewRootImpl$TraversalRunnable.run(ViewRootImpl.java:10540)
                                                                                                    	at android.view.Choreographer$CallbackRecord.run(Choreographer.java:1566)
                                                                                                    	at android.view.Choreographer$CallbackRecord.run(Choreographer.java:1575)
                                                                                                    	at android.view.Choreographer.doCallbacks(Choreographer.java:1175)
                                                                                                    	at android.view.Choreographer.doFrame(Choreographer.java:1104)
                                                                                                    	at android.view.Choreographer$FrameDisplayEventReceiver.run(Choreographer.java:1549)
                                                                                                    	at android.os.Handler.handleCallback(Handler.java:991)
                                                                                                    	at android.os.Handler.dispatchMessage(Handler.java:102)
                                                                                                    	at android.os.Looper.loopOnce(Looper.java:232)
                                                                                                    	at android.os.Looper.loop(Looper.java:317)
                                                                                                    	at android.app.ActivityThread.main(ActivityThread.java:8929)
                                                                                                    	at java.lang.reflect.Method.invoke(Native Method)
                                                                                                    	at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:591)
                                                                                                    	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:911)
--------- beginning of main
2025-12-17 16:24:47.862 15149-15149 AndroidRuntime          pid-15149                            E  FATAL EXCEPTION: main (Fix with AI)
                                                                                                    Process: com.aks_labs.pixelflow, PID: 15149
                                                                                                    java.lang.IllegalStateException: ViewTreeLifecycleOwner not found from androidx.compose.ui.platform.ComposeView{c834570 V.E...... ......I. 0,0-0,0}
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposer_androidKt.createLifecycleAwareWindowRecomposer(WindowRecomposer.android.kt:352)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposer_androidKt.createLifecycleAwareWindowRecomposer$default(WindowRecomposer.android.kt:325)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposerFactory$Companion$LifecycleAware$1.createRecomposer(WindowRecomposer.android.kt:168)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposerPolicy.createAndInstallWindowRecomposer$ui_release(WindowRecomposer.android.kt:224)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposer_androidKt.getWindowRecomposer(WindowRecomposer.android.kt:300)
                                                                                                    	at androidx.compose.ui.platform.AbstractComposeView.resolveParentCompositionContext(ComposeView.android.kt:244)
                                                                                                    	at androidx.compose.ui.platform.AbstractComposeView.ensureCompositionCreated(ComposeView.android.kt:251)
                                                                                                    	at androidx.compose.ui.platform.AbstractComposeView.onAttachedToWindow(ComposeView.android.kt:283)
                                                                                                    	at android.view.View.dispatchAttachedToWindow(View.java:23071)
                                                                                                    	at android.view.ViewGroup.dispatchAttachedToWindow(ViewGroup.java:3518)
                                                                                                    	at android.view.ViewRootImpl.performTraversals(ViewRootImpl.java:3571)
                                                                                                    	at android.view.ViewRootImpl.doTraversal(ViewRootImpl.java:3029)
                                                                                                    	at android.view.ViewRootImpl$TraversalRunnable.run(ViewRootImpl.java:10540)
                                                                                                    	at android.view.Choreographer$CallbackRecord.run(Choreographer.java:1566)
                                                                                                    	at android.view.Choreographer$CallbackRecord.run(Choreographer.java:1575)
                                                                                                    	at android.view.Choreographer.doCallbacks(Choreographer.java:1175)
                                                                                                    	at android.view.Choreographer.doFrame(Choreographer.java:1104)
                                                                                                    	at android.view.Choreographer$FrameDisplayEventReceiver.run(Choreographer.java:1549)
                                                                                                    	at android.os.Handler.handleCallback(Handler.java:991)
                                                                                                    	at android.os.Handler.dispatchMessage(Handler.java:102)
                                                                                                    	at android.os.Looper.loopOnce(Looper.java:232)
                                                                                                    	at android.os.Looper.loop(Looper.java:317)
                                                                                                    	at android.app.ActivityThread.main(ActivityThread.java:8929)
                                                                                                    	at java.lang.reflect.Method.invoke(Native Method)
                                                                                                    	at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:591)
                                                                                                    	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:911)
2025-12-17 16:24:53.307 15505-15505 _labs.pixelflow         com.aks_labs.pixelflow               I  Late-enabling -Xcheck:jni
2025-12-17 16:24:53.351 15505-15505 _labs.pixelflow         com.aks_labs.pixelflow               I  Using CollectorTypeCC GC.
2025-12-17 16:24:53.386 15505-15505 nativeloader            com.aks_labs.pixelflow               D  Load libframework-connectivity-tiramisu-jni.so using APEX ns com_android_tethering for caller /apex/com.android.tethering/javalib/framework-connectivity-t.jar: ok
2025-12-17 16:24:53.476 15505-15505 re-initialized>         com.aks_labs.pixelflow               W  type=1400 audit(0.0:7542): avc:  granted  { execute } for  path="/data/data/com.aks_labs.pixelflow/code_cache/startup_agents/1f04feac-agent.so" dev="mmcblk0p61" ino=3170446 scontext=u:r:untrusted_app_32:s0:c36,c259,c512,c768 tcontext=u:object_r:app_data_file:s0:c36,c259,c512,c768 tclass=file app=com.aks_labs.pixelflow
2025-12-17 16:24:53.494 15505-15505 nativeloader            com.aks_labs.pixelflow               D  Load /data/user/0/com.aks_labs.pixelflow/code_cache/startup_agents/1f04feac-agent.so using system ns (caller=<unknown>): ok
2025-12-17 16:24:53.517 15505-15505 _labs.pixelflow         com.aks_labs.pixelflow               W  DexFile /data/data/com.aks_labs.pixelflow/code_cache/.studio/instruments-843f6601.jar is in boot class path but is not in a known location
2025-12-17 16:24:53.618 15505-15505 _labs.pixelflow         com.aks_labs.pixelflow               W  Redefining intrinsic method java.lang.Thread java.lang.Thread.currentThread(). This may cause the unexpected use of the original definition of java.lang.Thread java.lang.Thread.currentThread()in methods that have already been compiled.
2025-12-17 16:24:53.618 15505-15505 _labs.pixelflow         com.aks_labs.pixelflow               W  Redefining intrinsic method boolean java.lang.Thread.interrupted(). This may cause the unexpected use of the original definition of boolean java.lang.Thread.interrupted()in methods that have already been compiled.
2025-12-17 16:24:53.710 15505-15505 ziparchive              com.aks_labs.pixelflow               W  Unable to open '/data/data/com.aks_labs.pixelflow/code_cache/.overlay/base.apk/classes7.dm': No such file or directory
2025-12-17 16:24:53.714 15505-15505 ziparchive              com.aks_labs.pixelflow               W  Unable to open '/data/data/com.aks_labs.pixelflow/code_cache/.overlay/base.apk/classes5.dm': No such file or directory
2025-12-17 16:24:53.735 15505-15505 ziparchive              com.aks_labs.pixelflow               W  Unable to open '/data/app/~~ToyYmJNiBKZb0xu3ligtrg==/com.aks_labs.pixelflow-IWUpK34O8i8Zt7WjPGuW6w==/base.dm': No such file or directory
2025-12-17 16:24:53.735 15505-15505 ziparchive              com.aks_labs.pixelflow               W  Unable to open '/data/app/~~ToyYmJNiBKZb0xu3ligtrg==/com.aks_labs.pixelflow-IWUpK34O8i8Zt7WjPGuW6w==/base.dm': No such file or directory
2025-12-17 16:24:54.255 15505-15505 nativeloader            com.aks_labs.pixelflow               D  Configuring clns-7 for other apk /data/app/~~ToyYmJNiBKZb0xu3ligtrg==/com.aks_labs.pixelflow-IWUpK34O8i8Zt7WjPGuW6w==/base.apk. target_sdk_version=33, uses_libraries=, library_path=/data/app/~~ToyYmJNiBKZb0xu3ligtrg==/com.aks_labs.pixelflow-IWUpK34O8i8Zt7WjPGuW6w==/lib/arm64, permitted_path=/data:/mnt/expand:/data/user/0/com.aks_labs.pixelflow
2025-12-17 16:24:54.271 15505-15505 _labs.pixelflow         com.aks_labs.pixelflow               I  AssetManager2(0x733c3824b8) locale list changing from [] to [en-IN]
2025-12-17 16:24:54.274 15505-15505 _labs.pixelflow         com.aks_labs.pixelflow               I  AssetManager2(0x733c378558) locale list changing from [] to [en-IN]
2025-12-17 16:24:54.289 15505-15505 GraphicsEnvironment     com.aks_labs.pixelflow               V  Currently set values for:
2025-12-17 16:24:54.290 15505-15505 GraphicsEnvironment     com.aks_labs.pixelflow               V    angle_gl_driver_selection_pkgs=[com.android.angle, com.linecorp.b612.android, com.campmobile.snow, com.google.android.apps.tachyon]
2025-12-17 16:24:54.290 15505-15505 GraphicsEnvironment     com.aks_labs.pixelflow               V    angle_gl_driver_selection_values=[angle, native, native, native]
2025-12-17 16:24:54.290 15505-15505 GraphicsEnvironment     com.aks_labs.pixelflow               V  com.aks_labs.pixelflow is not listed in per-application setting
2025-12-17 16:24:54.290 15505-15505 GraphicsEnvironment     com.aks_labs.pixelflow               V  Neither updatable production driver nor prerelease driver is supported.
2025-12-17 16:24:54.561 15505-15505 ScreenshotReceiver      com.aks_labs.pixelflow               D  ScreenshotBroadcastReceiver registered
2025-12-17 16:24:54.609 15505-15505 ViewBasedF...bleService com.aks_labs.pixelflow               D  Service onCreate called
2025-12-17 16:24:54.655 15505-15505 ViewBasedF...bleService com.aks_labs.pixelflow               D  Loaded 9 folders from SharedPrefsManager
2025-12-17 16:24:54.659 15505-15505 ViewBasedF...bleService com.aks_labs.pixelflow               D  Service onStartCommand called with action: null
2025-12-17 16:24:54.735 15505-15505 ComposeFlo...bleService com.aks_labs.pixelflow               D  Service onCreate called
2025-12-17 16:24:54.752 15505-15505 ComposeFlo...bleService com.aks_labs.pixelflow               D  Service onStartCommand called with action: null
2025-12-17 16:24:56.895 15505-15505 _labs.pixelflow         com.aks_labs.pixelflow               I  AssetManager2(0x733c37c6f8) locale list changing from [] to [en-IN]
2025-12-17 16:24:56.993 15505-15505 DesktopModeFlagsUtil    com.aks_labs.pixelflow               D  Toggle override initialized to: OVERRIDE_UNSET
2025-12-17 16:24:57.057 15505-15505 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  OnBackInvokedCallback is not enabled for the application.
                                                                                                    Set 'android:enableOnBackInvokedCallback="true"' in the application manifest.
2025-12-17 16:24:57.314 15505-15505 _labs.pixelflow         com.aks_labs.pixelflow               W  Method java.lang.Object androidx.compose.runtime.snapshots.SnapshotStateMap.mutate(kotlin.jvm.functions.Function1) failed lock verification and will run slower.
                                                                                                    Common causes for lock verification issues are non-optimized dex code
                                                                                                    and incorrect proguard optimizations.
2025-12-17 16:24:57.314 15505-15505 _labs.pixelflow         com.aks_labs.pixelflow               W  Method void androidx.compose.runtime.snapshots.SnapshotStateMap.update(kotlin.jvm.functions.Function1) failed lock verification and will run slower.
2025-12-17 16:24:57.315 15505-15505 _labs.pixelflow         com.aks_labs.pixelflow               W  Method boolean androidx.compose.runtime.snapshots.SnapshotStateMap.removeIf$runtime_release(kotlin.jvm.functions.Function1) failed lock verification and will run slower.
2025-12-17 16:24:57.655 15505-15650 AdrenoGLES-0            com.aks_labs.pixelflow               I  QUALCOMM build                   : 95db91f, Ifbc588260a
                                                                                                    Build Date                       : 09/24/20
                                                                                                    OpenGL ES Shader Compiler Version: EV031.32.02.01
                                                                                                    Local Branch                     : mybrancheafe5b6d-fb5b-f1b0-b904-5cb90179c3e0
                                                                                                    Remote Branch                    : quic/gfx-adreno.lnx.1.0.r114-rel
                                                                                                    Remote Branch                    : NONE
                                                                                                    Reconstruct Branch               : NOTHING
2025-12-17 16:24:57.655 15505-15650 AdrenoGLES-0            com.aks_labs.pixelflow               I  Build Config                     : S P 10.0.7 AArch64
2025-12-17 16:24:57.655 15505-15650 AdrenoGLES-0            com.aks_labs.pixelflow               I  Driver Path                      : /vendor/lib64/egl/libGLESv2_adreno.so
2025-12-17 16:24:57.670 15505-15650 AdrenoGLES-0            com.aks_labs.pixelflow               I  PFP: 0x016ee190, ME: 0x00000000
2025-12-17 16:24:57.734 15505-15657 Gralloc4                com.aks_labs.pixelflow               I  mapper 4.x is not supported
2025-12-17 16:24:57.736 15505-15657 Gralloc3                com.aks_labs.pixelflow               W  mapper 3.x is not supported
2025-12-17 16:24:57.742 15505-15505 _labs.pixelflow         com.aks_labs.pixelflow               W  Method boolean androidx.compose.runtime.snapshots.SnapshotStateList.conditionalUpdate(kotlin.jvm.functions.Function1) failed lock verification and will run slower.
2025-12-17 16:24:57.742 15505-15505 _labs.pixelflow         com.aks_labs.pixelflow               W  Method java.lang.Object androidx.compose.runtime.snapshots.SnapshotStateList.mutate(kotlin.jvm.functions.Function1) failed lock verification and will run slower.
2025-12-17 16:24:57.742 15505-15505 _labs.pixelflow         com.aks_labs.pixelflow               W  Method void androidx.compose.runtime.snapshots.SnapshotStateList.update(kotlin.jvm.functions.Function1) failed lock verification and will run slower.
2025-12-17 16:24:57.752 15505-15657 Gralloc2                com.aks_labs.pixelflow               I  Adding additional valid usage bits: 0x8202000
2025-12-17 16:24:57.795 15505-15505 MainActivity            com.aks_labs.pixelflow               D  Starting ComposeFloatingBubbleService
2025-12-17 16:24:57.795 15505-15505 MainActivity            com.aks_labs.pixelflow               D  Starting foreground service on Android O+
2025-12-17 16:24:57.798 15505-15505 MainActivity            com.aks_labs.pixelflow               D  Service start requested successfully
2025-12-17 16:24:57.807 15505-15505 Choreographer           com.aks_labs.pixelflow               I  Skipped 36 frames!  The application may be doing too much work on its main thread.
2025-12-17 16:24:58.589 15505-15581 HWUI                    com.aks_labs.pixelflow               I  Davey! duration=1379ms; Flags=0, FrameTimelineVsyncId=80483145, IntendedVsync=119416307345781, Vsync=119416907344137, InputEventId=0, HandleInputStart=119416917284391, AnimationStart=119416917290277, PerformTraversalsStart=119417057503662, DrawStart=119417650896214, FrameDeadline=119416907369535, FrameInterval=119416916844964, FrameStartTime=16666621, SyncQueued=119417669935277, SyncStart=119417670324027, IssueDrawCommandsStart=119417670423714, SwapBuffers=119417684600537, FrameCompleted=119417686940485, DequeueBufferDuration=15625, QueueBufferDuration=490261, GpuCompleted=119417686940485, SwapBuffersCompleted=119417686223089, DisplayPresentTime=0, CommandSubmissionCompleted=119417684600537, 
2025-12-17 16:24:58.620 15505-15505 ComposeFlo...bleService com.aks_labs.pixelflow               D  Service onStartCommand called with action: START_FROM_APP
2025-12-17 16:24:58.626 15505-15505 ComposeFlo...bleService com.aks_labs.pixelflow               D  Service starting from app
2025-12-17 16:24:58.626 15505-15505 ScreenshotDetector      com.aks_labs.pixelflow               D  Starting screenshot observation
2025-12-17 16:24:58.629 15505-15505 ScreenshotDetector      com.aks_labs.pixelflow               D  Successfully registered content observer
2025-12-17 16:24:58.629 15505-15505 ComposeFlo...bleService com.aks_labs.pixelflow               D  Screenshot detector started
2025-12-17 16:24:58.957 15505-15505 InsetsController        com.aks_labs.pixelflow               D  hide(ime(), fromIme=false)
2025-12-17 16:24:58.957 15505-15505 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:7ae00b21: onCancelled at PHASE_CLIENT_ALREADY_HIDDEN
2025-12-17 16:24:59.512 15505-15671 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:24:59.513 15505-15609 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:24:59.528 15505-15608 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:24:59.535 15505-15670 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:24:59.585 15505-15673 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:24:59.588 15505-15671 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:24:59.621 15505-15670 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:24:59.651 15505-15671 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:24:59.677 15505-15670 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:24:59.724 15505-15671 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:24:59.746 15505-15671 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:24:59.824 15505-15671 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:24:59.826 15505-15670 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:24:59.892 15505-15670 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:24:59.894 15505-15671 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:24:59.958 15505-15671 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:24:59.959 15505-15670 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:25:00.050 15505-15670 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:25:00.072 15505-15671 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:25:00.093 15505-15673 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:25:00.257 15505-15581 HWUI                    com.aks_labs.pixelflow               I  Davey! duration=1270ms; Flags=0, FrameTimelineVsyncId=80484997, IntendedVsync=119418074008879, Vsync=119418257343503, InputEventId=0, HandleInputStart=119418261011891, AnimationStart=119418261014755, PerformTraversalsStart=119418327014860, DrawStart=119418327136214, FrameDeadline=119418107339198, FrameInterval=119418260687360, FrameStartTime=16666784, SyncQueued=119419337946005, SyncStart=119419338175745, IssueDrawCommandsStart=119419338424755, SwapBuffers=119419341176318, FrameCompleted=119419344590849, DequeueBufferDuration=15782, QueueBufferDuration=264896, GpuCompleted=119419344590849, SwapBuffersCompleted=119419342316213, DisplayPresentTime=0, CommandSubmissionCompleted=119419341176318, 
2025-12-17 16:25:00.441 15505-15505 Choreographer           com.aks_labs.pixelflow               I  Skipped 76 frames!  The application may be doing too much work on its main thread.
2025-12-17 16:25:00.856 15505-15684 ProfileInstaller        com.aks_labs.pixelflow               D  Installing profile for com.aks_labs.pixelflow
2025-12-17 16:25:00.857 15505-15581 HWUI                    com.aks_labs.pixelflow               I  Davey! duration=1669ms; Flags=0, FrameTimelineVsyncId=80485258, IntendedVsync=119418274004323, Vsync=119419540652471, InputEventId=0, HandleInputStart=119419551177515, AnimationStart=119419551180484, PerformTraversalsStart=119419908150380, DrawStart=119419908277255, FrameDeadline=119419374017913, FrameInterval=119419550754599, FrameStartTime=16666423, SyncQueued=119419936989703, SyncStart=119419937324286, IssueDrawCommandsStart=119419937629442, SwapBuffers=119419940609755, FrameCompleted=119419943961005, DequeueBufferDuration=23438, QueueBufferDuration=345052, GpuCompleted=119419943961005, SwapBuffersCompleted=119419942021265, DisplayPresentTime=0, CommandSubmissionCompleted=119419940609755, 
2025-12-17 16:25:01.233 15505-15505 Choreographer           com.aks_labs.pixelflow               I  Skipped 47 frames!  The application may be doing too much work on its main thread.
2025-12-17 16:25:01.575 15505-15581 HWUI                    com.aks_labs.pixelflow               I  Davey! duration=1106ms; Flags=0, FrameTimelineVsyncId=80486236, IntendedVsync=119419554171334, Vsync=119420337500547, InputEventId=0, HandleInputStart=119420344146526, AnimationStart=119420344149963, PerformTraversalsStart=119420621718088, DrawStart=119420621820796, FrameDeadline=119419978646378, FrameInterval=119420342806890, FrameStartTime=16666579, SyncQueued=119420655645015, SyncStart=119420655709077, IssueDrawCommandsStart=119420655943557, SwapBuffers=119420657142515, FrameCompleted=119420661211890, DequeueBufferDuration=16146, QueueBufferDuration=268802, GpuCompleted=119420660756734, SwapBuffersCompleted=119420661211890, DisplayPresentTime=0, CommandSubmissionCompleted=119420657142515, 
2025-12-17 16:25:02.238 15505-15505 Choreographer           com.aks_labs.pixelflow               I  Skipped 32 frames!  The application may be doing too much work on its main thread.
2025-12-17 16:25:02.491 15505-15526 HWUI                    com.aks_labs.pixelflow               I  Davey! duration=774ms; Flags=0, FrameTimelineVsyncId=80486885, IntendedVsync=119420804167268, Vsync=119421337491652, InputEventId=0, HandleInputStart=119421347815327, AnimationStart=119421347819754, PerformTraversalsStart=119421559627202, DrawStart=119421559733973, FrameDeadline=119421092195837, FrameInterval=119421346916056, FrameStartTime=16666387, SyncQueued=119421572505796, SyncStart=119421572757879, IssueDrawCommandsStart=119421572952879, SwapBuffers=119421574459806, FrameCompleted=119421579112827, DequeueBufferDuration=15417, QueueBufferDuration=324687, GpuCompleted=119421579112827, SwapBuffersCompleted=119421575233192, DisplayPresentTime=0, CommandSubmissionCompleted=119421574459806, 
2025-12-17 16:25:03.723 15505-15581 HWUI                    com.aks_labs.pixelflow               I  Davey! duration=715ms; Flags=0, FrameTimelineVsyncId=80487579, IntendedVsync=119422104167631, Vsync=119422354164066, InputEventId=498231686, HandleInputStart=119422367158244, AnimationStart=119422367161369, PerformTraversalsStart=119422523508400, DrawStart=119422523633452, FrameDeadline=119422292174530, FrameInterval=119422366886056, FrameStartTime=16666429, SyncQueued=119422812920952, SyncStart=119422812982202, IssueDrawCommandsStart=119422813687775, SwapBuffers=119422817969337, FrameCompleted=119422819733868, DequeueBufferDuration=14532, QueueBufferDuration=335209, GpuCompleted=119422819733868, SwapBuffersCompleted=119422818776889, DisplayPresentTime=0, CommandSubmissionCompleted=119422817969337, 
2025-12-17 16:25:03.862 15505-15673 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:25:03.871 15505-15671 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:25:03.882 15505-15670 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:25:05.806 15505-15581 HWUI                    com.aks_labs.pixelflow               I  Davey! duration=890ms; Flags=0, FrameTimelineVsyncId=80488747, IntendedVsync=119424004181161, Vsync=119424004181161, InputEventId=-989379258, HandleInputStart=119424007176680, AnimationStart=119424007179180, PerformTraversalsStart=119424046437826, DrawStart=119424046554389, FrameDeadline=119424025514493, FrameInterval=119424007169180, FrameStartTime=16666898, SyncQueued=119424887813763, SyncStart=119424887889701, IssueDrawCommandsStart=119424888210638, SwapBuffers=119424889841315, FrameCompleted=119424894718399, DequeueBufferDuration=17396, QueueBufferDuration=261719, GpuCompleted=119424894718399, SwapBuffersCompleted=119424890561472, DisplayPresentTime=0, CommandSubmissionCompleted=119424889841315, 
2025-12-17 16:25:05.839 15505-15505 Choreographer           com.aks_labs.pixelflow               I  Skipped 55 frames!  The application may be doing too much work on its main thread.
2025-12-17 16:25:05.890 15505-15526 HWUI                    com.aks_labs.pixelflow               I  Davey! duration=959ms; Flags=0, FrameTimelineVsyncId=80488762, IntendedVsync=119424020847918, Vsync=119424937525383, InputEventId=0, HandleInputStart=119424949590326, AnimationStart=119424949593451, PerformTraversalsStart=119424966957097, DrawStart=119424967073034, FrameDeadline=119424925526985, FrameInterval=119424948395482, FrameStartTime=16666863, SyncQueued=119424973209909, SyncStart=119424973499701, IssueDrawCommandsStart=119424973692565, SwapBuffers=119424975225170, FrameCompleted=119424980359493, DequeueBufferDuration=15937, QueueBufferDuration=256355, GpuCompleted=119424980359493, SwapBuffersCompleted=119424975926159, DisplayPresentTime=0, CommandSubmissionCompleted=119424975225170, 
2025-12-17 16:25:06.235 15505-15520 _labs.pixelflow         com.aks_labs.pixelflow               I  Compiler allocated 4762KB to compile void com.aks_labs.pixelflow.ui.screens.ImprovedHomeScreenKt$ImprovedHomeScreen$13.invoke(androidx.compose.foundation.layout.PaddingValues, androidx.compose.runtime.Composer, int)
2025-12-17 16:25:07.622 15505-15505 InsetsController        com.aks_labs.pixelflow               D  hide(ime(), fromIme=false)
2025-12-17 16:25:07.622 15505-15505 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:18ab2bff: onCancelled at PHASE_CLIENT_ALREADY_HIDDEN
2025-12-17 16:25:09.007 15505-15525 HWUI                    com.aks_labs.pixelflow               I  Davey! duration=762ms; Flags=0, FrameTimelineVsyncId=80491236, IntendedVsync=119427337507212, Vsync=119427654176241, InputEventId=0, HandleInputStart=119427655748450, AnimationStart=119427655750950, PerformTraversalsStart=119427798396366, DrawStart=119427798479439, FrameDeadline=119427492171602, FrameInterval=119427655485273, FrameStartTime=16666791, SyncQueued=119428092465950, SyncStart=119428092522825, IssueDrawCommandsStart=119428092789179, SwapBuffers=119428094341939, FrameCompleted=119428100495481, DequeueBufferDuration=15937, QueueBufferDuration=350104, GpuCompleted=119428100495481, SwapBuffersCompleted=119428095173033, DisplayPresentTime=0, CommandSubmissionCompleted=119428094341939, 
2025-12-17 16:25:09.007 15505-15525 HWUI                    com.aks_labs.pixelflow               I  Davey! duration=762ms; Flags=0, FrameTimelineVsyncId=80491236, IntendedVsync=119427337507212, Vsync=119427654176241, InputEventId=469654328, HandleInputStart=119427655748450, AnimationStart=119427655750950, PerformTraversalsStart=119427798396366, DrawStart=119428092906939, FrameDeadline=119427358840544, FrameInterval=119427655485273, FrameStartTime=16666791, SyncQueued=119428096829543, SyncStart=119428097305898, IssueDrawCommandsStart=119428097389127, SwapBuffers=119428099203033, FrameCompleted=119428100773762, DequeueBufferDuration=16614, QueueBufferDuration=194166, GpuCompleted=119428100773762, SwapBuffersCompleted=119428099785793, DisplayPresentTime=0, CommandSubmissionCompleted=119428099203033, 
2025-12-17 16:25:09.443 15505-15505 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  sendCancelIfRunning: isInProgress=false callback=android.view.ViewRootImpl$$ExternalSyntheticLambda13@e5e747
2025-12-17 16:25:09.449 15505-15650 HWUI                    com.aks_labs.pixelflow               D  endAllActiveAnimators on 0x727c3fcf60 (UnprojectedRipple) with handle 0x728c3bbbb0
2025-12-17 16:25:09.677 15505-15505 InsetsController        com.aks_labs.pixelflow               D  hide(ime(), fromIme=false)
2025-12-17 16:25:09.677 15505-15505 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:40227dd7: onCancelled at PHASE_CLIENT_ALREADY_HIDDEN
2025-12-17 16:25:10.654 15505-15505 ViewBasedF...bleService com.aks_labs.pixelflow               D  Service onDestroy called
2025-12-17 16:25:10.654 15505-15505 ViewBasedF...bleService com.aks_labs.pixelflow               D  Hiding drag zones - START
2025-12-17 16:25:10.654 15505-15505 ViewBasedF...bleService com.aks_labs.pixelflow               D  No drag zones view to remove
2025-12-17 16:25:10.654 15505-15505 ViewBasedF...bleService com.aks_labs.pixelflow               D  Drag zones hidden successfully
2025-12-17 16:25:10.654 15505-15505 ViewBasedF...bleService com.aks_labs.pixelflow               D  Hiding action buttons
2025-12-17 16:25:11.173 15505-15505 ViewBasedF...bleService com.aks_labs.pixelflow               D  Service onCreate called
2025-12-17 16:25:11.180 15505-15505 ViewBasedF...bleService com.aks_labs.pixelflow               D  Loaded 9 folders from SharedPrefsManager
2025-12-17 16:25:11.180 15505-15505 ViewBasedF...bleService com.aks_labs.pixelflow               D  Service onStartCommand called with action: START_FROM_APP
2025-12-17 16:25:11.186 15505-15505 ViewBasedF...bleService com.aks_labs.pixelflow               D  Service starting from app
2025-12-17 16:25:11.186 15505-15505 ViewBasedF...bleService com.aks_labs.pixelflow               D  Starting screenshot detection
2025-12-17 16:25:11.204 15505-15720 ViewBasedF...bleService com.aks_labs.pixelflow               D  Found recent screenshot during startup: /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968887622.jpg, Already processed: false
2025-12-17 16:25:11.247 15505-15505 ViewBasedF...bleService com.aks_labs.pixelflow               D  New screenshot detected: /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968887622.jpg
2025-12-17 16:25:11.248 15505-15505 ViewBasedF...bleService com.aks_labs.pixelflow               D  Screenshot file exists, size: 443077 bytes
2025-12-17 16:25:11.288 15505-15505 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:25:11.293 15505-15505 ViewBasedF...bleService com.aks_labs.pixelflow               D  Broadcast sent for screenshot detection: /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968887622.jpg
2025-12-17 16:25:11.293 15505-15505 ViewBasedF...bleService com.aks_labs.pixelflow               D  Showing floating bubble for screenshot
2025-12-17 16:25:11.346 15505-15505 ViewBasedF...bleService com.aks_labs.pixelflow               D  Floating bubble shown successfully
2025-12-17 16:25:11.385 15505-15505 ScreenshotReceiver      com.aks_labs.pixelflow               D  Received broadcast: com.aks_labs.pixelflow.SCREENSHOT_DETECTED
2025-12-17 16:25:11.385 15505-15505 ScreenshotReceiver      com.aks_labs.pixelflow               D  Screenshot detected: /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968887622.jpg
2025-12-17 16:25:28.494 15505-15670 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Starting refresh for 9 albums
2025-12-17 16:25:28.532 15505-15670 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Posts' (id=1): found 1 thumbnails
2025-12-17 16:25:28.532 15505-15670 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Posts': /storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png
2025-12-17 16:25:28.542 15505-15670 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Docs' (id=2): found 1 thumbnails
2025-12-17 16:25:28.542 15505-15670 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Docs': /storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png
2025-12-17 16:25:28.547 15505-15670 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Chats' (id=3): found 1 thumbnails
2025-12-17 16:25:28.548 15505-15670 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Chats': /storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png
2025-12-17 16:25:28.556 15505-15670 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Payments' (id=4): found 1 thumbnails
2025-12-17 16:25:28.556 15505-15670 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Payments': /storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png
2025-12-17 16:25:28.591 15505-15670 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Memes' (id=5): found 1 thumbnails
2025-12-17 16:25:28.592 15505-15670 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Memes': /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-152755_PixelFlow.png
2025-12-17 16:25:28.600 15505-15670 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Tweets' (id=6): found 1 thumbnails
2025-12-17 16:25:28.600 15505-15670 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Tweets': /storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251216-130240_PixelFlow.png
2025-12-17 16:25:28.606 15505-15671 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Starting refresh for 9 albums
2025-12-17 16:25:28.610 15505-15670 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Quotes' (id=7): found 1 thumbnails
2025-12-17 16:25:28.610 15505-15670 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Quotes': /storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png
2025-12-17 16:25:28.613 15505-15671 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Posts' (id=1): found 1 thumbnails
2025-12-17 16:25:28.613 15505-15671 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Posts': /storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png
2025-12-17 16:25:28.615 15505-15670 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Messages' (id=8): found 1 thumbnails
2025-12-17 16:25:28.615 15505-15670 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Messages': /storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png
2025-12-17 16:25:28.619 15505-15671 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Docs' (id=2): found 1 thumbnails
2025-12-17 16:25:28.619 15505-15671 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Docs': /storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png
2025-12-17 16:25:28.623 15505-15671 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Chats' (id=3): found 1 thumbnails
2025-12-17 16:25:28.624 15505-15671 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Chats': /storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png
2025-12-17 16:25:28.630 15505-15671 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Payments' (id=4): found 1 thumbnails
2025-12-17 16:25:28.630 15505-15671 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Payments': /storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png
2025-12-17 16:25:28.636 15505-15670 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Unsorted' (id=9): found 1 thumbnails
2025-12-17 16:25:28.636 15505-15670 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Unsorted': /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968887622.jpg
2025-12-17 16:25:28.636 15505-15670 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Refresh complete. Total thumbnails: 9
2025-12-17 16:25:28.636 15505-15670 FolderScreen            com.aks_labs.pixelflow               D  After refresh - albumToThumbnailMapping size: 9
2025-12-17 16:25:28.636 15505-15670 FolderScreen            com.aks_labs.pixelflow               D  Album Posts (id=1): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png
2025-12-17 16:25:28.637 15505-15670 FolderScreen            com.aks_labs.pixelflow               D  Album Docs (id=2): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png
2025-12-17 16:25:28.637 15505-15670 FolderScreen            com.aks_labs.pixelflow               D  Album Chats (id=3): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png
2025-12-17 16:25:28.637 15505-15670 FolderScreen            com.aks_labs.pixelflow               D  Album Payments (id=4): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png
2025-12-17 16:25:28.637 15505-15670 FolderScreen            com.aks_labs.pixelflow               D  Album Memes (id=5): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-152755_PixelFlow.png
2025-12-17 16:25:28.637 15505-15670 FolderScreen            com.aks_labs.pixelflow               D  Album Tweets (id=6): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251216-130240_PixelFlow.png
2025-12-17 16:25:28.637 15505-15670 FolderScreen            com.aks_labs.pixelflow               D  Album Quotes (id=7): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png
2025-12-17 16:25:28.637 15505-15670 FolderScreen            com.aks_labs.pixelflow               D  Album Messages (id=8): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png
2025-12-17 16:25:28.637 15505-15670 FolderScreen            com.aks_labs.pixelflow               D  Album Unsorted (id=9): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968887622.jpg
2025-12-17 16:25:28.642 15505-15671 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Memes' (id=5): found 1 thumbnails
2025-12-17 16:25:28.642 15505-15671 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Memes': /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-152755_PixelFlow.png
2025-12-17 16:25:28.644 15505-15505 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Posts, itemId: -804332824, filePath: /storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png
2025-12-17 16:25:28.647 15505-15671 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Tweets' (id=6): found 1 thumbnails
2025-12-17 16:25:28.647 15505-15671 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Tweets': /storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251216-130240_PixelFlow.png
2025-12-17 16:25:28.654 15505-15671 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Quotes' (id=7): found 1 thumbnails
2025-12-17 16:25:28.654 15505-15671 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Quotes': /storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png
2025-12-17 16:25:28.657 15505-15671 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Messages' (id=8): found 1 thumbnails
2025-12-17 16:25:28.657 15505-15671 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Messages': /storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png
2025-12-17 16:25:28.659 15505-15505 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png, exists: true
2025-12-17 16:25:28.666 15505-15671 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Unsorted' (id=9): found 1 thumbnails
2025-12-17 16:25:28.666 15505-15671 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Unsorted': /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968887622.jpg
2025-12-17 16:25:28.666 15505-15671 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Refresh complete. Total thumbnails: 9
2025-12-17 16:25:28.666 15505-15671 FolderScreen            com.aks_labs.pixelflow               D  After refresh - albumToThumbnailMapping size: 9
2025-12-17 16:25:28.666 15505-15671 FolderScreen            com.aks_labs.pixelflow               D  Album Posts (id=1): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png
2025-12-17 16:25:28.666 15505-15671 FolderScreen            com.aks_labs.pixelflow               D  Album Docs (id=2): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png
2025-12-17 16:25:28.666 15505-15671 FolderScreen            com.aks_labs.pixelflow               D  Album Chats (id=3): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png
2025-12-17 16:25:28.667 15505-15671 FolderScreen            com.aks_labs.pixelflow               D  Album Payments (id=4): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png
2025-12-17 16:25:28.667 15505-15671 FolderScreen            com.aks_labs.pixelflow               D  Album Memes (id=5): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-152755_PixelFlow.png
2025-12-17 16:25:28.667 15505-15671 FolderScreen            com.aks_labs.pixelflow               D  Album Tweets (id=6): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251216-130240_PixelFlow.png
2025-12-17 16:25:28.667 15505-15671 FolderScreen            com.aks_labs.pixelflow               D  Album Quotes (id=7): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png
2025-12-17 16:25:28.667 15505-15671 FolderScreen            com.aks_labs.pixelflow               D  Album Messages (id=8): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png
2025-12-17 16:25:28.667 15505-15671 FolderScreen            com.aks_labs.pixelflow               D  Album Unsorted (id=9): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968887622.jpg
2025-12-17 16:25:28.682 15505-15505 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Docs, itemId: 1440477992, filePath: /storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png
2025-12-17 16:25:28.689 15505-15505 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png, exists: true
2025-12-17 16:25:28.710 15505-15505 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Chats, itemId: 2035652294, filePath: /storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png
2025-12-17 16:25:28.717 15505-15505 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png, exists: true
2025-12-17 16:25:28.739 15505-15505 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Payments, itemId: 2057259762, filePath: /storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png
2025-12-17 16:25:28.753 15505-15505 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png, exists: true
2025-12-17 16:25:28.763 15505-15671 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:25:28.774 15505-15670 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:25:28.785 15505-15673 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:25:28.789 15505-15505 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Memes, itemId: 1668404524, filePath: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-152755_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-152755_PixelFlow.png
2025-12-17 16:25:28.795 15505-15505 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-152755_PixelFlow.png, exists: true
2025-12-17 16:25:28.818 15505-15505 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Tweets, itemId: -559454833, filePath: /storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251216-130240_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251216-130240_PixelFlow.png
2025-12-17 16:25:28.825 15505-15505 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251216-130240_PixelFlow.png, exists: true
2025-12-17 16:25:28.834 15505-15670 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:25:28.847 15505-15505 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Quotes, itemId: 1719613194, filePath: /storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png
2025-12-17 16:25:28.854 15505-15505 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png, exists: true
2025-12-17 16:25:28.876 15505-15505 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Messages, itemId: 150555577, filePath: /storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png
2025-12-17 16:25:28.882 15505-15505 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png, exists: true
2025-12-17 16:25:28.888 15505-15670 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:25:28.906 15505-15505 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Unsorted, itemId: -178079059, filePath: /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968887622.jpg, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968887622.jpg
2025-12-17 16:25:28.908 15505-15673 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:25:28.912 15505-15505 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968887622.jpg, exists: true
2025-12-17 16:25:29.010 15505-15673 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:25:29.052 15505-15670 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:25:29.086 15505-15505 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Posts, itemId: -804332824, filePath: /storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png
2025-12-17 16:25:29.089 15505-15505 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png, exists: true
2025-12-17 16:25:29.091 15505-15505 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Docs, itemId: 1440477992, filePath: /storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png
2025-12-17 16:25:29.093 15505-15505 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png, exists: true
2025-12-17 16:25:29.095 15505-15505 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Chats, itemId: 2035652294, filePath: /storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png
2025-12-17 16:25:29.097 15505-15505 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png, exists: true
2025-12-17 16:25:29.099 15505-15505 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Payments, itemId: 2057259762, filePath: /storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png
2025-12-17 16:25:29.101 15505-15505 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png, exists: true
2025-12-17 16:25:29.104 15505-15505 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Memes, itemId: 1668404524, filePath: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-152755_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-152755_PixelFlow.png
2025-12-17 16:25:29.106 15505-15505 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-152755_PixelFlow.png, exists: true
2025-12-17 16:25:29.108 15505-15505 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Tweets, itemId: -559454833, filePath: /storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251216-130240_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251216-130240_PixelFlow.png
2025-12-17 16:25:29.110 15505-15505 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251216-130240_PixelFlow.png, exists: true
2025-12-17 16:25:29.113 15505-15505 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Quotes, itemId: 1719613194, filePath: /storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png
2025-12-17 16:25:29.115 15505-15505 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png, exists: true
2025-12-17 16:25:29.117 15505-15505 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Messages, itemId: 150555577, filePath: /storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png
2025-12-17 16:25:29.120 15505-15505 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png, exists: true
2025-12-17 16:25:29.123 15505-15505 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Unsorted, itemId: -178079059, filePath: /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968887622.jpg, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968887622.jpg
2025-12-17 16:25:29.125 15505-15505 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968887622.jpg, exists: true
2025-12-17 16:25:29.281 15505-15505 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Posts, itemId: -804332824, filePath: /storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png
2025-12-17 16:25:29.283 15505-15505 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png, exists: true
2025-12-17 16:25:29.285 15505-15505 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Docs, itemId: 1440477992, filePath: /storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png
2025-12-17 16:25:29.288 15505-15505 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png, exists: true
2025-12-17 16:25:29.290 15505-15505 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Chats, itemId: 2035652294, filePath: /storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png
2025-12-17 16:25:29.292 15505-15505 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png, exists: true
2025-12-17 16:25:29.294 15505-15505 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Payments, itemId: 2057259762, filePath: /storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png
2025-12-17 16:25:29.295 15505-15505 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png, exists: true
2025-12-17 16:25:29.297 15505-15505 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Memes, itemId: 1668404524, filePath: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-152755_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-152755_PixelFlow.png
2025-12-17 16:25:29.299 15505-15505 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-152755_PixelFlow.png, exists: true
2025-12-17 16:25:29.301 15505-15505 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Tweets, itemId: -559454833, filePath: /storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251216-130240_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251216-130240_PixelFlow.png
2025-12-17 16:25:29.303 15505-15505 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251216-130240_PixelFlow.png, exists: true
2025-12-17 16:25:29.305 15505-15505 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Quotes, itemId: 1719613194, filePath: /storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png
2025-12-17 16:25:29.307 15505-15505 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png, exists: true
2025-12-17 16:25:29.309 15505-15505 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Messages, itemId: 150555577, filePath: /storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png
2025-12-17 16:25:29.311 15505-15505 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png, exists: true
2025-12-17 16:25:29.313 15505-15505 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Unsorted, itemId: -178079059, filePath: /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968887622.jpg, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968887622.jpg
2025-12-17 16:25:29.314 15505-15505 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968887622.jpg, exists: true
2025-12-17 16:25:30.088 15505-15505 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Posts, itemId: -804332824, filePath: /storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png
2025-12-17 16:25:30.089 15505-15505 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png, exists: true
2025-12-17 16:25:30.092 15505-15505 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Docs, itemId: 1440477992, filePath: /storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png
2025-12-17 16:25:30.094 15505-15505 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png, exists: true
2025-12-17 16:25:30.096 15505-15505 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Chats, itemId: 2035652294, filePath: /storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png
2025-12-17 16:25:30.098 15505-15505 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png, exists: true
2025-12-17 16:25:30.100 15505-15505 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Payments, itemId: 2057259762, filePath: /storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png
2025-12-17 16:25:30.102 15505-15505 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png, exists: true
2025-12-17 16:25:30.104 15505-15505 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Memes, itemId: 1668404524, filePath: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-152755_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-152755_PixelFlow.png
2025-12-17 16:25:30.107 15505-15505 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-152755_PixelFlow.png, exists: true
2025-12-17 16:25:30.109 15505-15505 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Tweets, itemId: -559454833, filePath: /storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251216-130240_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251216-130240_PixelFlow.png
2025-12-17 16:25:30.111 15505-15505 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251216-130240_PixelFlow.png, exists: true
2025-12-17 16:25:30.113 15505-15505 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Quotes, itemId: 1719613194, filePath: /storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png
2025-12-17 16:25:30.115 15505-15505 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png, exists: true
2025-12-17 16:25:30.117 15505-15505 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Messages, itemId: 150555577, filePath: /storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png
2025-12-17 16:25:30.119 15505-15505 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png, exists: true
2025-12-17 16:25:30.121 15505-15505 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Unsorted, itemId: -178079059, filePath: /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968887622.jpg, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968887622.jpg
2025-12-17 16:25:30.123 15505-15505 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968887622.jpg, exists: true
2025-12-17 16:25:49.225 15505-15505 SwipeToScreenShot       com.aks_labs.pixelflow               D  canceling motionEvent because of threeGesture detecting
2025-12-17 16:25:49.236 15505-15505 SwipeToScreenShot       com.aks_labs.pixelflow               D  canceling motionEvent because of threeGesture detecting
2025-12-17 16:25:49.247 15505-15505 SwipeToScreenShot       com.aks_labs.pixelflow               D  canceling motionEvent because of threeGesture detecting
2025-12-17 16:25:49.264 15505-15505 SwipeToScreenShot       com.aks_labs.pixelflow               D  canceling motionEvent because of threeGesture detecting
2025-12-17 16:25:49.279 15505-15505 SwipeToScreenShot       com.aks_labs.pixelflow               D  canceling motionEvent because of threeGesture detecting
2025-12-17 16:25:49.296 15505-15505 SwipeToScreenShot       com.aks_labs.pixelflow               D  canceling motionEvent because of threeGesture detecting
2025-12-17 16:25:49.313 15505-15505 SwipeToScreenShot       com.aks_labs.pixelflow               D  canceling motionEvent because of threeGesture detecting
2025-12-17 16:25:49.330 15505-15505 SwipeToScreenShot       com.aks_labs.pixelflow               D  canceling motionEvent because of threeGesture detecting
2025-12-17 16:25:49.346 15505-15505 SwipeToScreenShot       com.aks_labs.pixelflow               D  canceling motionEvent because of threeGesture detecting
2025-12-17 16:25:49.363 15505-15505 SwipeToScreenShot       com.aks_labs.pixelflow               D  canceling motionEvent because of threeGesture detecting
2025-12-17 16:25:49.380 15505-15505 SwipeToScreenShot       com.aks_labs.pixelflow               D  canceling motionEvent because of threeGesture detecting
2025-12-17 16:25:49.391 15505-15505 SwipeToScreenShot       com.aks_labs.pixelflow               D  canceling motionEvent because of threeGesture detecting
2025-12-17 16:25:49.392 15505-15505 SwipeToScreenShot       com.aks_labs.pixelflow               D  canceling motionEvent because of threeGesture detecting
2025-12-17 16:25:49.611 15505-15505 ScreenshotDetector      com.aks_labs.pixelflow               D  Content change detected: content://media/external/images/media/1000004912
2025-12-17 16:25:49.614 15505-15505 ViewBasedF...bleService com.aks_labs.pixelflow               D  Content change detected: content://media/external/images/media/1000004912
2025-12-17 16:25:49.616 15505-15670 ScreenshotDetector      com.aks_labs.pixelflow               D  Checking if URI is a screenshot: content://media/external/images/media/1000004912
2025-12-17 16:25:49.616 15505-15670 ScreenshotDetector      com.aks_labs.pixelflow               D  Querying for images added after: 1765968944
2025-12-17 16:25:49.617 15505-16200 ViewBasedF...bleService com.aks_labs.pixelflow               D  Checking if URI is a screenshot: content://media/external/images/media/1000004912
2025-12-17 16:25:49.617 15505-16200 ViewBasedF...bleService com.aks_labs.pixelflow               D  Querying for images added after: 1765968939
2025-12-17 16:25:49.634 15505-15670 ScreenshotDetector      com.aks_labs.pixelflow               D  Query returned 1 results
2025-12-17 16:25:49.634 15505-15670 ScreenshotDetector      com.aks_labs.pixelflow               D  Found image: Screenshot_20251217-162549_PixelFlow.png at /storage/emulated/0/Pictures/Screenshots/.pending-1766573749-Screenshot_20251217-162549_PixelFlow.png
2025-12-17 16:25:49.641 15505-15670 ScreenshotDetector      com.aks_labs.pixelflow               D  Is screenshot by name: true, File exists: true
2025-12-17 16:25:49.641 15505-15670 ScreenshotDetector      com.aks_labs.pixelflow               D  Screenshot detected: /storage/emulated/0/Pictures/Screenshots/.pending-1766573749-Screenshot_20251217-162549_PixelFlow.png
2025-12-17 16:25:49.642 15505-15505 ComposeFlo...bleService com.aks_labs.pixelflow               D  New screenshot detected: /storage/emulated/0/Pictures/Screenshots/.pending-1766573749-Screenshot_20251217-162549_PixelFlow.png
2025-12-17 16:25:49.644 15505-15505 ComposeFlo...bleService com.aks_labs.pixelflow               D  Screenshot file is still pending, waiting for it to be ready
2025-12-17 16:25:49.685 15505-16200 ViewBasedF...bleService com.aks_labs.pixelflow               D  Query returned 0 results
2025-12-17 16:25:50.146 15505-15670 ComposeFlo...bleService com.aks_labs.pixelflow               D  Waiting for screenshot file to be ready, attempt 1
2025-12-17 16:25:50.308 15505-15505 ScreenshotDetector      com.aks_labs.pixelflow               D  Content change detected: content://media/external/images/media/1000004912
2025-12-17 16:25:50.308 15505-15505 ViewBasedF...bleService com.aks_labs.pixelflow               D  Content change detected: content://media/external/images/media/1000004912
2025-12-17 16:25:50.309 15505-15670 ScreenshotDetector      com.aks_labs.pixelflow               D  Checking if URI is a screenshot: content://media/external/images/media/1000004912
2025-12-17 16:25:50.309 15505-15670 ScreenshotDetector      com.aks_labs.pixelflow               D  Querying for images added after: 1765968945
2025-12-17 16:25:50.309 15505-16204 ViewBasedF...bleService com.aks_labs.pixelflow               D  Checking if URI is a screenshot: content://media/external/images/media/1000004912
2025-12-17 16:25:50.309 15505-16204 ViewBasedF...bleService com.aks_labs.pixelflow               D  Querying for images added after: 1765968940
2025-12-17 16:25:50.319 15505-15670 ScreenshotDetector      com.aks_labs.pixelflow               D  Query returned 1 results
2025-12-17 16:25:50.319 15505-15670 ScreenshotDetector      com.aks_labs.pixelflow               D  Found image: Screenshot_20251217-162549_PixelFlow.png at /storage/emulated/0/Pictures/Screenshots/Screenshot_20251217-162549_PixelFlow.png
2025-12-17 16:25:50.320 15505-15670 ScreenshotDetector      com.aks_labs.pixelflow               D  Is screenshot by name: true, File exists: true
2025-12-17 16:25:50.320 15505-15670 ScreenshotDetector      com.aks_labs.pixelflow               D  Screenshot detected: /storage/emulated/0/Pictures/Screenshots/Screenshot_20251217-162549_PixelFlow.png
2025-12-17 16:25:50.321 15505-15505 ComposeFlo...bleService com.aks_labs.pixelflow               D  New screenshot detected: /storage/emulated/0/Pictures/Screenshots/Screenshot_20251217-162549_PixelFlow.png
2025-12-17 16:25:50.321 15505-15505 ComposeFlo...bleService com.aks_labs.pixelflow               D  Screenshot file exists, size: 530886 bytes
2025-12-17 16:25:50.322 15505-15505 ComposeFlo...bleService com.aks_labs.pixelflow               D  Showing floating bubble for screenshot
2025-12-17 16:25:50.324 15505-16204 ViewBasedF...bleService com.aks_labs.pixelflow               D  Query returned 1 results
2025-12-17 16:25:50.324 15505-16204 ViewBasedF...bleService com.aks_labs.pixelflow               D  Found image: Screenshot_20251217-162549_PixelFlow.png at /storage/emulated/0/Pictures/Screenshots/Screenshot_20251217-162549_PixelFlow.png
2025-12-17 16:25:50.325 15505-16204 ViewBasedF...bleService com.aks_labs.pixelflow               D  Is screenshot by name: true, File exists: true, Already processed: false
2025-12-17 16:25:50.325 15505-16204 ViewBasedF...bleService com.aks_labs.pixelflow               D  Screenshot detected: /storage/emulated/0/Pictures/Screenshots/Screenshot_20251217-162549_PixelFlow.png
2025-12-17 16:25:50.390 15505-15505 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:25:50.415 15505-15505 ComposeFlo...bleService com.aks_labs.pixelflow               D  Floating bubble shown successfully
2025-12-17 16:25:50.415 15505-15505 ViewBasedF...bleService com.aks_labs.pixelflow               D  New screenshot detected: /storage/emulated/0/Pictures/Screenshots/Screenshot_20251217-162549_PixelFlow.png
2025-12-17 16:25:50.418 15505-15505 ViewBasedF...bleService com.aks_labs.pixelflow               D  Screenshot file is still pending, waiting for it to be ready
2025-12-17 16:25:50.419 15505-15505 ScreenshotDetector      com.aks_labs.pixelflow               D  Content change detected: content://media/external/images/media/1000004912
2025-12-17 16:25:50.419 15505-16206 ViewBasedF...bleService com.aks_labs.pixelflow               D  Waiting for screenshot file to be ready, attempt 1
2025-12-17 16:25:50.419 15505-15505 ViewBasedF...bleService com.aks_labs.pixelflow               D  Content change detected: content://media/external/images/media/1000004912
2025-12-17 16:25:50.420 15505-15505 ViewBasedF...bleService com.aks_labs.pixelflow               D  Content change detected: content://media/external/images/media/1000004913
2025-12-17 16:25:50.420 15505-15673 ScreenshotDetector      com.aks_labs.pixelflow               D  Checking if URI is a screenshot: content://media/external/images/media/1000004912
2025-12-17 16:25:50.420 15505-15673 ScreenshotDetector      com.aks_labs.pixelflow               D  Querying for images added after: 1765968945
2025-12-17 16:25:50.420 15505-15505 ScreenshotDetector      com.aks_labs.pixelflow               D  Content change detected: content://media/external/images/media/1000004913
2025-12-17 16:25:50.420 15505-16208 ViewBasedF...bleService com.aks_labs.pixelflow               D  Checking if URI is a screenshot: content://media/external/images/media/1000004913
2025-12-17 16:25:50.420 15505-16208 ViewBasedF...bleService com.aks_labs.pixelflow               D  Querying for images added after: 1765968940
2025-12-17 16:25:50.420 15505-16207 ViewBasedF...bleService com.aks_labs.pixelflow               D  Checking if URI is a screenshot: content://media/external/images/media/1000004912
2025-12-17 16:25:50.421 15505-16207 ViewBasedF...bleService com.aks_labs.pixelflow               D  Querying for images added after: 1765968940
2025-12-17 16:25:50.421 15505-15608 ScreenshotDetector      com.aks_labs.pixelflow               D  Checking if URI is a screenshot: content://media/external/images/media/1000004913
2025-12-17 16:25:50.421 15505-15608 ScreenshotDetector      com.aks_labs.pixelflow               D  Querying for images added after: 1765968945
2025-12-17 16:25:50.422 15505-15505 ScreenshotDetector      com.aks_labs.pixelflow               D  Content change detected: content://media/external/images/media/1000004912
2025-12-17 16:25:50.422 15505-15505 ViewBasedF...bleService com.aks_labs.pixelflow               D  Content change detected: content://media/external/images/media/1000004912
2025-12-17 16:25:50.424 15505-15671 ScreenshotDetector      com.aks_labs.pixelflow               D  Checking if URI is a screenshot: content://media/external/images/media/1000004912
2025-12-17 16:25:50.424 15505-15671 ScreenshotDetector      com.aks_labs.pixelflow               D  Querying for images added after: 1765968945
2025-12-17 16:25:50.426 15505-15505 AndroidRuntime          com.aks_labs.pixelflow               D  Shutting down VM
2025-12-17 16:25:50.426 15505-16209 ViewBasedF...bleService com.aks_labs.pixelflow               D  Checking if URI is a screenshot: content://media/external/images/media/1000004912
2025-12-17 16:25:50.426 15505-16209 ViewBasedF...bleService com.aks_labs.pixelflow               D  Querying for images added after: 1765968940
2025-12-17 16:25:50.429 15505-15670 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:25:50.436 15505-15673 ScreenshotDetector      com.aks_labs.pixelflow               D  Query returned 0 results
2025-12-17 16:25:50.437 15505-15673 ScreenshotDetector      com.aks_labs.pixelflow               D  No images found in the cursor
2025-12-17 16:25:50.437 15505-15608 ScreenshotDetector      com.aks_labs.pixelflow               D  Query returned 1 results
2025-12-17 16:25:50.437 15505-15608 ScreenshotDetector      com.aks_labs.pixelflow               D  Found image: screenshot_1765968950323.jpg at /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968950323.jpg
2025-12-17 16:25:50.437 15505-15608 ScreenshotDetector      com.aks_labs.pixelflow               D  Is screenshot by name: true, File exists: true
2025-12-17 16:25:50.437 15505-15608 ScreenshotDetector      com.aks_labs.pixelflow               D  Screenshot detected: /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968950323.jpg
2025-12-17 16:25:50.439 15505-16208 ViewBasedF...bleService com.aks_labs.pixelflow               D  Query returned 1 results
2025-12-17 16:25:50.439 15505-16208 ViewBasedF...bleService com.aks_labs.pixelflow               D  Found image: screenshot_1765968950323.jpg at /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968950323.jpg
2025-12-17 16:25:50.440 15505-16207 ViewBasedF...bleService com.aks_labs.pixelflow               D  Query returned 1 results
2025-12-17 16:25:50.440 15505-16208 ViewBasedF...bleService com.aks_labs.pixelflow               D  Is screenshot by name: true, File exists: true, Already processed: false
2025-12-17 16:25:50.440 15505-16207 ViewBasedF...bleService com.aks_labs.pixelflow               D  Found image: screenshot_1765968950323.jpg at /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968950323.jpg
2025-12-17 16:25:50.440 15505-16208 ViewBasedF...bleService com.aks_labs.pixelflow               D  Screenshot detected: /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968950323.jpg
2025-12-17 16:25:50.440 15505-16207 ViewBasedF...bleService com.aks_labs.pixelflow               D  Is screenshot by name: true, File exists: true, Already processed: false
2025-12-17 16:25:50.440 15505-16207 ViewBasedF...bleService com.aks_labs.pixelflow               D  Screenshot detected: /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968950323.jpg
2025-12-17 16:25:50.446 15505-16209 ViewBasedF...bleService com.aks_labs.pixelflow               D  Query returned 1 results
2025-12-17 16:25:50.446 15505-16209 ViewBasedF...bleService com.aks_labs.pixelflow               D  Found image: screenshot_1765968950323.jpg at /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968950323.jpg
2025-12-17 16:25:50.447 15505-16209 ViewBasedF...bleService com.aks_labs.pixelflow               D  Is screenshot by name: true, File exists: true, Already processed: false
2025-12-17 16:25:50.447 15505-16209 ViewBasedF...bleService com.aks_labs.pixelflow               D  Screenshot detected: /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968950323.jpg
2025-12-17 16:25:50.453 15505-15671 ScreenshotDetector      com.aks_labs.pixelflow               D  Query returned 0 results
2025-12-17 16:25:50.453 15505-15671 ScreenshotDetector      com.aks_labs.pixelflow               D  No images found in the cursor
2025-12-17 16:25:50.460 15505-15505 AndroidRuntime          com.aks_labs.pixelflow               E  FATAL EXCEPTION: main (Fix with AI)
                                                                                                    Process: com.aks_labs.pixelflow, PID: 15505
                                                                                                    java.lang.IllegalStateException: ViewTreeLifecycleOwner not found from androidx.compose.ui.platform.ComposeView{f509b95 V.E...... ......I. 0,0-0,0}
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposer_androidKt.createLifecycleAwareWindowRecomposer(WindowRecomposer.android.kt:352)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposer_androidKt.createLifecycleAwareWindowRecomposer$default(WindowRecomposer.android.kt:325)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposerFactory$Companion$LifecycleAware$1.createRecomposer(WindowRecomposer.android.kt:168)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposerPolicy.createAndInstallWindowRecomposer$ui_release(WindowRecomposer.android.kt:224)
                                                                                                    	at androidx.compose.ui.platform.WindowRecomposer_androidKt.getWindowRecomposer(WindowRecomposer.android.kt:300)
                                                                                                    	at androidx.compose.ui.platform.AbstractComposeView.resolveParentCompositionContext(ComposeView.android.kt:244)
                                                                                                    	at androidx.compose.ui.platform.AbstractComposeView.ensureCompositionCreated(ComposeView.android.kt:251)
                                                                                                    	at androidx.compose.ui.platform.AbstractComposeView.onAttachedToWindow(ComposeView.android.kt:283)
                                                                                                    	at android.view.View.dispatchAttachedToWindow(View.java:23071)
                                                                                                    	at android.view.ViewGroup.dispatchAttachedToWindow(ViewGroup.java:3518)
                                                                                                    	at android.view.ViewRootImpl.performTraversals(ViewRootImpl.java:3571)
                                                                                                    	at android.view.ViewRootImpl.doTraversal(ViewRootImpl.java:3029)
                                                                                                    	at android.view.ViewRootImpl$TraversalRunnable.run(ViewRootImpl.java:10540)
                                                                                                    	at android.view.Choreographer$CallbackRecord.run(Choreographer.java:1566)
                                                                                                    	at android.view.Choreographer$CallbackRecord.run(Choreographer.java:1575)
                                                                                                    	at android.view.Choreographer.doCallbacks(Choreographer.java:1175)
                                                                                                    	at android.view.Choreographer.doFrame(Choreographer.java:1104)
                                                                                                    	at android.view.Choreographer$FrameDisplayEventReceiver.run(Choreographer.java:1549)
                                                                                                    	at android.os.Handler.handleCallback(Handler.java:991)
                                                                                                    	at android.os.Handler.dispatchMessage(Handler.java:102)
                                                                                                    	at android.os.Looper.loopOnce(Looper.java:232)
                                                                                                    	at android.os.Looper.loop(Looper.java:317)
                                                                                                    	at android.app.ActivityThread.main(ActivityThread.java:8929)
                                                                                                    	at java.lang.reflect.Method.invoke(Native Method)
                                                                                                    	at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:591)
                                                                                                    	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:911)
2025-12-17 16:25:50.482 15505-15670 ComposeFlo...bleService com.aks_labs.pixelflow               D  Screenshot saved successfully to Unsorted folder
2025-12-17 16:25:50.488 15505-15670 ComposeFlo...bleService com.aks_labs.pixelflow               D  Screenshot added to folder: 9
2025-12-17 16:25:50.647 15505-15670 ComposeFlo...bleService com.aks_labs.pixelflow               D  Waiting for screenshot file to be ready, attempt 2
2025-12-17 16:25:50.922 15505-16206 ViewBasedF...bleService com.aks_labs.pixelflow               D  Waiting for screenshot file to be ready, attempt 2
2025-12-17 16:25:51.157 15505-15670 ComposeFlo...bleService com.aks_labs.pixelflow               D  Waiting for screenshot file to be ready, attempt 3
2025-12-17 16:25:51.425 15505-16206 ViewBasedF...bleService com.aks_labs.pixelflow               D  Waiting for screenshot file to be ready, attempt 3
2025-12-17 16:25:51.662 15505-15670 ComposeFlo...bleService com.aks_labs.pixelflow               D  Waiting for screenshot file to be ready, attempt 4
2025-12-17 16:25:51.927 15505-16206 ViewBasedF...bleService com.aks_labs.pixelflow               D  Waiting for screenshot file to be ready, attempt 4
2025-12-17 16:25:52.166 15505-15670 ComposeFlo...bleService com.aks_labs.pixelflow               D  Waiting for screenshot file to be ready, attempt 5
2025-12-17 16:25:52.392 15505-15670 ComposeFlo...bleService com.aks_labs.pixelflow               D  Found recent screenshot in Screenshots directory: /storage/emulated/0/Pictures/Screenshots/Screenshot_20251217-162047_PixelFlow.png
2025-12-17 16:25:52.392 15505-15670 ComposeFlo...bleService com.aks_labs.pixelflow               D  New screenshot detected: /storage/emulated/0/Pictures/Screenshots/Screenshot_20251217-162047_PixelFlow.png
2025-12-17 16:25:52.393 15505-15670 ComposeFlo...bleService com.aks_labs.pixelflow               D  Screenshot file exists, size: 307049 bytes
2025-12-17 16:25:52.440 15505-16206 ViewBasedF...bleService com.aks_labs.pixelflow               D  Waiting for screenshot file to be ready, attempt 5
2025-12-17 16:25:52.486 15505-15671 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:25:52.508 15505-15671 ComposeFlo...bleService com.aks_labs.pixelflow               D  Screenshot saved successfully to Unsorted folder
2025-12-17 16:25:52.510 15505-15671 ComposeFlo...bleService com.aks_labs.pixelflow               D  Screenshot added to folder: 9
2025-12-17 16:25:52.943 15505-16206 ViewBasedF...bleService com.aks_labs.pixelflow               D  Waiting for screenshot file to be ready, attempt 6
2025-12-17 16:25:53.446 15505-16206 ViewBasedF...bleService com.aks_labs.pixelflow               D  Waiting for screenshot file to be ready, attempt 7
2025-12-17 16:25:53.949 15505-16206 ViewBasedF...bleService com.aks_labs.pixelflow               D  Waiting for screenshot file to be ready, attempt 8
2025-12-17 16:25:54.455 15505-16206 ViewBasedF...bleService com.aks_labs.pixelflow               D  Waiting for screenshot file to be ready, attempt 9
2025-12-17 16:25:54.959 15505-16206 ViewBasedF...bleService com.aks_labs.pixelflow               D  Waiting for screenshot file to be ready, attempt 10
