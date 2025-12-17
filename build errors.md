---------------------------- PROCESS STARTED (20080) for package com.aks_labs.pixelflow ----------------------------
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
--------- beginning of system
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
2025-12-17 15:57:33.274  1985-2312  AppOps                  pid-1985                             E  Operation not started: uid=10804 pkg=com.aks_labs.pixelflow(null) op=VIBRATE
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
2025-12-17 15:57:43.276  1985-11675 AppOps                  pid-1985                             E  Operation not started: uid=10804 pkg=com.aks_labs.pixelflow(null) op=SYSTEM_ALERT_WINDOW
2025-12-17 16:01:13.103  1985-2125  VerityUtils             pid-1985                             E  Failed to check whether fs-verity is enabled, errno 38: /data/app/~~XzmhQS5wj0aZKx-eLiMCfA==/com.aks_labs.pixelflow-PLMRaS-KAQs8Wktz0WInsA==/base.apk
2025-12-17 16:01:13.325  1985-2125  VerityUtils             pid-1985                             E  Failed to check whether fs-verity is enabled, errno 38: /data/app/~~XzmhQS5wj0aZKx-eLiMCfA==/com.aks_labs.pixelflow-PLMRaS-KAQs8Wktz0WInsA==/base.apk
2025-12-17 16:20:33.367  1985-2125  VerityUtils             pid-1985                             E  Failed to check whether fs-verity is enabled, errno 38: /data/app/~~ToyYmJNiBKZb0xu3ligtrg==/com.aks_labs.pixelflow-IWUpK34O8i8Zt7WjPGuW6w==/base.apk
2025-12-17 16:20:33.956  1985-2125  VerityUtils             pid-1985                             E  Failed to check whether fs-verity is enabled, errno 38: /data/app/~~ToyYmJNiBKZb0xu3ligtrg==/com.aks_labs.pixelflow-IWUpK34O8i8Zt7WjPGuW6w==/base.apk
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
2025-12-17 16:25:50.460 15505-15505 AndroidRuntime          pid-15505                            E  FATAL EXCEPTION: main (Fix with AI)
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
2025-12-17 16:26:04.940  1985-7210  AppOps                  system_server                        E  Operation not started: uid=10804 pkg=com.aks_labs.pixelflow(null) op=SYSTEM_ALERT_WINDOW
--------- beginning of main
2025-12-17 16:40:08.837  1985-2321  InputDispatcher         system_server                        E  channel '341ad81 com.aks_labs.pixelflow/com.aks_labs.pixelflow.MainActivity' ~ Channel is unrecoverably broken and will be disposed!
2025-12-17 16:40:12.645 20080-20080 _labs.pixelflow         com.aks_labs.pixelflow               I  Late-enabling -Xcheck:jni
2025-12-17 16:40:12.723 20080-20080 _labs.pixelflow         com.aks_labs.pixelflow               I  Using CollectorTypeCC GC.
2025-12-17 16:40:12.869 20080-20080 nativeloader            com.aks_labs.pixelflow               D  Load libframework-connectivity-tiramisu-jni.so using APEX ns com_android_tethering for caller /apex/com.android.tethering/javalib/framework-connectivity-t.jar: ok
2025-12-17 16:40:13.867 20080-20080 ziparchive              com.aks_labs.pixelflow               W  Unable to open '/data/app/~~Kz-5jZIxyoxCcKtqjsZq0Q==/com.aks_labs.pixelflow-6cOUvVIFCOgWmVGexOsjVw==/base.dm': No such file or directory
2025-12-17 16:40:13.867 20080-20080 ziparchive              com.aks_labs.pixelflow               W  Unable to open '/data/app/~~Kz-5jZIxyoxCcKtqjsZq0Q==/com.aks_labs.pixelflow-6cOUvVIFCOgWmVGexOsjVw==/base.dm': No such file or directory
2025-12-17 16:40:14.349 20080-20080 nativeloader            com.aks_labs.pixelflow               D  Configuring clns-7 for other apk /data/app/~~Kz-5jZIxyoxCcKtqjsZq0Q==/com.aks_labs.pixelflow-6cOUvVIFCOgWmVGexOsjVw==/base.apk. target_sdk_version=33, uses_libraries=, library_path=/data/app/~~Kz-5jZIxyoxCcKtqjsZq0Q==/com.aks_labs.pixelflow-6cOUvVIFCOgWmVGexOsjVw==/lib/arm64, permitted_path=/data:/mnt/expand:/data/user/0/com.aks_labs.pixelflow
2025-12-17 16:40:14.413 20080-20080 _labs.pixelflow         com.aks_labs.pixelflow               I  AssetManager2(0x733c3824b8) locale list changing from [] to [en-IN]
2025-12-17 16:40:14.490 20080-20080 GraphicsEnvironment     com.aks_labs.pixelflow               V  Currently set values for:
2025-12-17 16:40:14.490 20080-20080 GraphicsEnvironment     com.aks_labs.pixelflow               V    angle_gl_driver_selection_pkgs=[com.android.angle, com.linecorp.b612.android, com.campmobile.snow, com.google.android.apps.tachyon]
2025-12-17 16:40:14.490 20080-20080 GraphicsEnvironment     com.aks_labs.pixelflow               V    angle_gl_driver_selection_values=[angle, native, native, native]
2025-12-17 16:40:14.490 20080-20080 GraphicsEnvironment     com.aks_labs.pixelflow               V  com.aks_labs.pixelflow is not listed in per-application setting
2025-12-17 16:40:14.490 20080-20080 GraphicsEnvironment     com.aks_labs.pixelflow               V  Neither updatable production driver nor prerelease driver is supported.
2025-12-17 16:40:15.306 20080-20080 ScreenshotReceiver      com.aks_labs.pixelflow               D  ScreenshotBroadcastReceiver registered
2025-12-17 16:40:15.327 20080-20080 BootReceiver            com.aks_labs.pixelflow               D  Received intent: android.intent.action.BOOT_COMPLETED
2025-12-17 16:40:15.327 20080-20080 BootReceiver            com.aks_labs.pixelflow               D  Starting FloatingBubbleService after boot or app update
2025-12-17 16:40:15.406 20080-20080 _labs.pixelflow         com.aks_labs.pixelflow               I  AssetManager2(0x733c379818) locale list changing from [] to [en-IN]
2025-12-17 16:40:15.546 20080-20080 DesktopModeFlagsUtil    com.aks_labs.pixelflow               D  Toggle override initialized to: OVERRIDE_UNSET
2025-12-17 16:40:15.649 20080-20080 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  OnBackInvokedCallback is not enabled for the application.
                                                                                                    Set 'android:enableOnBackInvokedCallback="true"' in the application manifest.
2025-12-17 16:40:16.020 20080-20080 _labs.pixelflow         com.aks_labs.pixelflow               W  Method java.lang.Object androidx.compose.runtime.snapshots.SnapshotStateMap.mutate(kotlin.jvm.functions.Function1) failed lock verification and will run slower.
                                                                                                    Common causes for lock verification issues are non-optimized dex code
                                                                                                    and incorrect proguard optimizations.
2025-12-17 16:40:16.020 20080-20080 _labs.pixelflow         com.aks_labs.pixelflow               W  Method void androidx.compose.runtime.snapshots.SnapshotStateMap.update(kotlin.jvm.functions.Function1) failed lock verification and will run slower.
2025-12-17 16:40:16.021 20080-20080 _labs.pixelflow         com.aks_labs.pixelflow               W  Method boolean androidx.compose.runtime.snapshots.SnapshotStateMap.removeIf$runtime_release(kotlin.jvm.functions.Function1) failed lock verification and will run slower.
2025-12-17 16:40:16.285 20080-20134 AdrenoGLES-0            com.aks_labs.pixelflow               I  QUALCOMM build                   : 95db91f, Ifbc588260a
                                                                                                    Build Date                       : 09/24/20
                                                                                                    OpenGL ES Shader Compiler Version: EV031.32.02.01
                                                                                                    Local Branch                     : mybrancheafe5b6d-fb5b-f1b0-b904-5cb90179c3e0
                                                                                                    Remote Branch                    : quic/gfx-adreno.lnx.1.0.r114-rel
                                                                                                    Remote Branch                    : NONE
                                                                                                    Reconstruct Branch               : NOTHING
2025-12-17 16:40:16.285 20080-20134 AdrenoGLES-0            com.aks_labs.pixelflow               I  Build Config                     : S P 10.0.7 AArch64
2025-12-17 16:40:16.285 20080-20134 AdrenoGLES-0            com.aks_labs.pixelflow               I  Driver Path                      : /vendor/lib64/egl/libGLESv2_adreno.so
2025-12-17 16:40:16.292  1985-2125  VerityUtils             system_server                        E  Failed to check whether fs-verity is enabled, errno 38: /data/app/~~Kz-5jZIxyoxCcKtqjsZq0Q==/com.aks_labs.pixelflow-6cOUvVIFCOgWmVGexOsjVw==/base.apk
2025-12-17 16:40:16.348 20080-20080 _labs.pixelflow         com.aks_labs.pixelflow               W  Method boolean androidx.compose.runtime.snapshots.SnapshotStateList.conditionalUpdate(kotlin.jvm.functions.Function1) failed lock verification and will run slower.
2025-12-17 16:40:16.348 20080-20080 _labs.pixelflow         com.aks_labs.pixelflow               W  Method java.lang.Object androidx.compose.runtime.snapshots.SnapshotStateList.mutate(kotlin.jvm.functions.Function1) failed lock verification and will run slower.
2025-12-17 16:40:16.348 20080-20080 _labs.pixelflow         com.aks_labs.pixelflow               W  Method void androidx.compose.runtime.snapshots.SnapshotStateList.update(kotlin.jvm.functions.Function1) failed lock verification and will run slower.
2025-12-17 16:40:16.349 20080-20134 AdrenoGLES-0            com.aks_labs.pixelflow               I  PFP: 0x016ee190, ME: 0x00000000
2025-12-17 16:40:16.662  1985-2125  VerityUtils             system_server                        E  Failed to check whether fs-verity is enabled, errno 38: /data/app/~~Kz-5jZIxyoxCcKtqjsZq0Q==/com.aks_labs.pixelflow-6cOUvVIFCOgWmVGexOsjVw==/base.apk
2025-12-17 16:40:16.742 20080-20216 Gralloc4                com.aks_labs.pixelflow               I  mapper 4.x is not supported
2025-12-17 16:40:16.742 20080-20216 Gralloc3                com.aks_labs.pixelflow               W  mapper 3.x is not supported
2025-12-17 16:40:16.760 20080-20216 Gralloc2                com.aks_labs.pixelflow               I  Adding additional valid usage bits: 0x8202000
2025-12-17 16:40:16.812 20080-20092 HWUI                    com.aks_labs.pixelflow               I  Davey! duration=1012ms; Flags=1, FrameTimelineVsyncId=80981904, IntendedVsync=120334870900864, Vsync=120334870900864, InputEventId=0, HandleInputStart=120334873968260, AnimationStart=120334873973052, PerformTraversalsStart=120334873975760, DrawStart=120335804588051, FrameDeadline=120334892234196, FrameInterval=120334873951906, FrameStartTime=16666680, SyncQueued=120335809336697, SyncStart=120335809510447, IssueDrawCommandsStart=120335810202062, SwapBuffers=120335877813312, FrameCompleted=120335883448780, DequeueBufferDuration=66433021, QueueBufferDuration=411459, GpuCompleted=120335883448780, SwapBuffersCompleted=120335878711333, DisplayPresentTime=0, CommandSubmissionCompleted=120335877813312, 
2025-12-17 16:40:16.827 20080-20080 MainActivity            com.aks_labs.pixelflow               D  Starting ViewBasedFloatingBubbleService
2025-12-17 16:40:16.827 20080-20080 MainActivity            com.aks_labs.pixelflow               D  Starting foreground service on Android O+
2025-12-17 16:40:16.834 20080-20080 MainActivity            com.aks_labs.pixelflow               D  Service start requested successfully
2025-12-17 16:40:16.866 20080-20080 Choreographer           com.aks_labs.pixelflow               I  Skipped 60 frames!  The application may be doing too much work on its main thread.
2025-12-17 16:40:17.898 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Service onCreate called
2025-12-17 16:40:17.924 20080-20092 HWUI                    com.aks_labs.pixelflow               I  Davey! duration=2050ms; Flags=0, FrameTimelineVsyncId=80981968, IntendedVsync=120334970893719, Vsync=120335970891819, InputEventId=0, HandleInputStart=120335975614926, AnimationStart=120335975620760, PerformTraversalsStart=120336134265030, DrawStart=120336923740030, FrameDeadline=120335908901676, FrameInterval=120335975070343, FrameStartTime=16666635, SyncQueued=120336940403311, SyncStart=120336940462790, IssueDrawCommandsStart=120336940562738, SwapBuffers=120337017738988, FrameCompleted=120337021450811, DequeueBufferDuration=15990, QueueBufferDuration=1815730, GpuCompleted=120337021450811, SwapBuffersCompleted=120337020326593, DisplayPresentTime=0, CommandSubmissionCompleted=120337017738988, 
2025-12-17 16:40:17.929 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Loaded 9 folders from SharedPrefsManager
2025-12-17 16:40:17.929 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Service onStartCommand called with action: START_FROM_APP
2025-12-17 16:40:17.941 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Service starting from app
2025-12-17 16:40:17.941 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Starting screenshot detection
2025-12-17 16:40:18.318 20080-20080 InsetsController        com.aks_labs.pixelflow               D  hide(ime(), fromIme=false)
2025-12-17 16:40:18.319 20080-20080 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:41adc192: onCancelled at PHASE_CLIENT_ALREADY_HIDDEN
2025-12-17 16:40:18.703 20080-20194 HWUI                    com.aks_labs.pixelflow               D  --- Failed to create image decoder with message 'unimplemented'
2025-12-17 16:40:18.703 20080-20199 HWUI                    com.aks_labs.pixelflow               D  --- Failed to create image decoder with message 'unimplemented'
2025-12-17 16:40:18.703 20080-20193 HWUI                    com.aks_labs.pixelflow               D  --- Failed to create image decoder with message 'unimplemented'
2025-12-17 16:40:18.705 20080-20194 HWUI                    com.aks_labs.pixelflow               D  --- Failed to create image decoder with message 'unimplemented'
2025-12-17 16:40:18.705 20080-20199 HWUI                    com.aks_labs.pixelflow               D  --- Failed to create image decoder with message 'unimplemented'
2025-12-17 16:40:18.705 20080-20193 HWUI                    com.aks_labs.pixelflow               D  --- Failed to create image decoder with message 'unimplemented'
2025-12-17 16:40:18.771 20080-20199 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:40:18.771 20080-20260 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:40:18.827 20080-20260 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:40:18.830 20080-20194 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:40:18.861 20080-20199 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:40:18.921 20080-20260 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:40:18.932 20080-20199 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:40:18.968 20080-20194 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:40:18.978 20080-20194 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:40:18.989 20080-20199 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:40:19.039 20080-20194 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:40:19.062 20080-20199 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:40:19.088 20080-20199 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:40:19.109 20080-20194 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:40:19.139 20080-20194 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:40:19.150 20080-20199 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:40:19.218 20080-20199 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:40:19.283 20080-20080 BootReceiver            com.aks_labs.pixelflow               D  Service started successfully after boot
2025-12-17 16:40:19.290 20080-20250 HWUI                    com.aks_labs.pixelflow               I  Davey! duration=953ms; Flags=0, FrameTimelineVsyncId=80983492, IntendedVsync=120337437558968, Vsync=120337570891216, InputEventId=0, HandleInputStart=120337583627426, AnimationStart=120337583630551, PerformTraversalsStart=120337628922426, DrawStart=120337629037686, FrameDeadline=120337508895461, FrameInterval=120337583082530, FrameStartTime=16666531, SyncQueued=120338385501279, SyncStart=120338385729561, IssueDrawCommandsStart=120338385967582, SwapBuffers=120338388519873, FrameCompleted=120338391730759, DequeueBufferDuration=24219, QueueBufferDuration=256406, GpuCompleted=120338391730759, SwapBuffersCompleted=120338389958623, DisplayPresentTime=0, CommandSubmissionCompleted=120338388519873, 
2025-12-17 16:40:19.420 20080-20080 Choreographer           com.aks_labs.pixelflow               I  Skipped 56 frames!  The application may be doing too much work on its main thread.
2025-12-17 16:40:19.698 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Service onStartCommand called with action: START_ON_BOOT
2025-12-17 16:40:19.707 20080-20250 HWUI                    com.aks_labs.pixelflow               I  Davey! duration=1207ms; Flags=0, FrameTimelineVsyncId=80983571, IntendedVsync=120337587563525, Vsync=120338520894749, InputEventId=0, HandleInputStart=120338530601279, AnimationStart=120338530607946, PerformTraversalsStart=120338774987842, DrawStart=120338775103571, FrameDeadline=120338425551098, FrameInterval=120338529836488, FrameStartTime=16666629, SyncQueued=120338790247998, SyncStart=120338790484300, IssueDrawCommandsStart=120338790681227, SwapBuffers=120338792069300, FrameCompleted=120338795248467, DequeueBufferDuration=15417, QueueBufferDuration=215937, GpuCompleted=120338795248467, SwapBuffersCompleted=120338793045967, DisplayPresentTime=0, CommandSubmissionCompleted=120338792069300, 
2025-12-17 16:40:19.949 20080-20080 Choreographer           com.aks_labs.pixelflow               I  Skipped 31 frames!  The application may be doing too much work on its main thread.
2025-12-17 16:40:20.206 20080-20092 HWUI                    com.aks_labs.pixelflow               I  Davey! duration=761ms; Flags=0, FrameTimelineVsyncId=80983997, IntendedVsync=120338537572495, Vsync=120339054242241, InputEventId=0, HandleInputStart=120339059174821, AnimationStart=120339059178050, PerformTraversalsStart=120339267057529, DrawStart=120339267151540, FrameDeadline=120338825560774, FrameInterval=120339058534196, FrameStartTime=16666766, SyncQueued=120339287862269, SyncStart=120339288150862, IssueDrawCommandsStart=120339288382685, SwapBuffers=120339289677894, FrameCompleted=120339299349925, DequeueBufferDuration=16354, QueueBufferDuration=259688, GpuCompleted=120339298943206, SwapBuffersCompleted=120339299349925, DisplayPresentTime=0, CommandSubmissionCompleted=120339289677894, 
2025-12-17 16:40:20.930 20080-20268 ProfileInstaller        com.aks_labs.pixelflow               D  Installing profile for com.aks_labs.pixelflow
2025-12-17 16:40:22.085 20080-20084 _labs.pixelflow         com.aks_labs.pixelflow               I  Compiler allocated 4762KB to compile void com.aks_labs.pixelflow.ui.screens.ImprovedHomeScreenKt$ImprovedHomeScreen$13.invoke(androidx.compose.foundation.layout.PaddingValues, androidx.compose.runtime.Composer, int)
2025-12-17 16:40:22.850 20080-20085 _labs.pixelflow         com.aks_labs.pixelflow               I  Background concurrent copying GC freed 16MB AllocSpace bytes, 47(940KB) LOS objects, 49% free, 6674KB/13MB, paused 72us,31us total 102.246ms
2025-12-17 16:40:30.342 20080-20080 BootReceiver            com.aks_labs.pixelflow               D  Service started successfully after boot
2025-12-17 16:40:30.343 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Service onStartCommand called with action: START_ON_BOOT
2025-12-17 16:40:37.458 20080-20080 InsetsController        com.aks_labs.pixelflow               D  hide(ime(), fromIme=false)
2025-12-17 16:40:37.459 20080-20080 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:7915327c: onCancelled at PHASE_CLIENT_ALREADY_HIDDEN
2025-12-17 16:40:38.569 20080-20080 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  sendCancelIfRunning: isInProgress=false callback=android.view.ViewRootImpl$$ExternalSyntheticLambda13@dc128e9
2025-12-17 16:40:38.575 20080-20134 HWUI                    com.aks_labs.pixelflow               D  endAllActiveAnimators on 0x727c3dc5f0 (UnprojectedRipple) with handle 0x728c4006d0
2025-12-17 16:40:38.626 20080-20080 InsetsController        com.aks_labs.pixelflow               D  hide(ime(), fromIme=false)
2025-12-17 16:40:38.626 20080-20080 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:79e1e504: onCancelled at PHASE_CLIENT_ALREADY_HIDDEN
2025-12-17 16:40:38.674 20080-20085 _labs.pixelflow         com.aks_labs.pixelflow               W  Cleared Reference was only reachable from finalizer (only reported once)
2025-12-17 16:40:39.170 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Service onDestroy called
2025-12-17 16:40:39.170 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Hiding drag zones - START
2025-12-17 16:40:39.170 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  No drag zones view to remove
2025-12-17 16:40:39.170 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Drag zones hidden successfully
2025-12-17 16:40:39.170 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Hiding action buttons
2025-12-17 16:40:39.685 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Service onCreate called
2025-12-17 16:40:39.697 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Loaded 9 folders from SharedPrefsManager
2025-12-17 16:40:39.698 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Service onStartCommand called with action: START_FROM_APP
2025-12-17 16:40:39.705 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Service starting from app
2025-12-17 16:40:39.705 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Starting screenshot detection
2025-12-17 16:40:41.126 20080-20199 HWUI                    com.aks_labs.pixelflow               D  --- Failed to create image decoder with message 'unimplemented'
2025-12-17 16:40:41.126 20080-20199 HWUI                    com.aks_labs.pixelflow               D  --- Failed to create image decoder with message 'unimplemented'
2025-12-17 16:40:41.126 20080-20194 HWUI                    com.aks_labs.pixelflow               D  --- Failed to create image decoder with message 'unimplemented'
2025-12-17 16:40:41.127 20080-20194 HWUI                    com.aks_labs.pixelflow               D  --- Failed to create image decoder with message 'unimplemented'
2025-12-17 16:40:41.133 20080-20194 HWUI                    com.aks_labs.pixelflow               D  --- Failed to create image decoder with message 'unimplemented'
2025-12-17 16:40:41.134 20080-20194 HWUI                    com.aks_labs.pixelflow               D  --- Failed to create image decoder with message 'unimplemented'
2025-12-17 16:40:43.572 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Content change detected: content://media/external/images/media/1000004923
2025-12-17 16:40:43.573 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Content change detected: content://media/external/images/media/1000004923
2025-12-17 16:40:43.573 20080-20371 ViewBasedF...bleService com.aks_labs.pixelflow               D  Checking if URI is a screenshot: content://media/external/images/media/1000004923
2025-12-17 16:40:43.573 20080-20371 ViewBasedF...bleService com.aks_labs.pixelflow               D  Querying for images added after: 1765969833
2025-12-17 16:40:43.573 20080-20372 ViewBasedF...bleService com.aks_labs.pixelflow               D  Checking if URI is a screenshot: content://media/external/images/media/1000004923
2025-12-17 16:40:43.573 20080-20372 ViewBasedF...bleService com.aks_labs.pixelflow               D  Querying for images added after: 1765969833
2025-12-17 16:40:43.627 20080-20371 ViewBasedF...bleService com.aks_labs.pixelflow               D  Query returned 0 results
2025-12-17 16:40:43.628 20080-20372 ViewBasedF...bleService com.aks_labs.pixelflow               D  Query returned 0 results
2025-12-17 16:40:44.292 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Content change detected: content://media/external/images/media/1000004923
2025-12-17 16:40:44.293 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Content change detected: content://media/external/images/media/1000004923
2025-12-17 16:40:44.293 20080-20381 ViewBasedF...bleService com.aks_labs.pixelflow               D  Checking if URI is a screenshot: content://media/external/images/media/1000004923
2025-12-17 16:40:44.293 20080-20381 ViewBasedF...bleService com.aks_labs.pixelflow               D  Querying for images added after: 1765969834
2025-12-17 16:40:44.294 20080-20380 ViewBasedF...bleService com.aks_labs.pixelflow               D  Checking if URI is a screenshot: content://media/external/images/media/1000004923
2025-12-17 16:40:44.294 20080-20380 ViewBasedF...bleService com.aks_labs.pixelflow               D  Querying for images added after: 1765969834
2025-12-17 16:40:44.314 20080-20381 ViewBasedF...bleService com.aks_labs.pixelflow               D  Query returned 1 results
2025-12-17 16:40:44.314 20080-20381 ViewBasedF...bleService com.aks_labs.pixelflow               D  Found image: Screenshot_20251217-164043_System UI.png at /storage/emulated/0/Pictures/Screenshots/Screenshot_20251217-164043_System UI.png
2025-12-17 16:40:44.316 20080-20380 ViewBasedF...bleService com.aks_labs.pixelflow               D  Query returned 1 results
2025-12-17 16:40:44.316 20080-20380 ViewBasedF...bleService com.aks_labs.pixelflow               D  Found image: Screenshot_20251217-164043_System UI.png at /storage/emulated/0/Pictures/Screenshots/Screenshot_20251217-164043_System UI.png
2025-12-17 16:40:44.317 20080-20381 ViewBasedF...bleService com.aks_labs.pixelflow               D  Is screenshot by name: true, File exists: true, Already processed: false
2025-12-17 16:40:44.317 20080-20381 ViewBasedF...bleService com.aks_labs.pixelflow               D  Screenshot detected: /storage/emulated/0/Pictures/Screenshots/Screenshot_20251217-164043_System UI.png
2025-12-17 16:40:44.317 20080-20380 ViewBasedF...bleService com.aks_labs.pixelflow               D  Is screenshot by name: true, File exists: true, Already processed: false
2025-12-17 16:40:44.317 20080-20380 ViewBasedF...bleService com.aks_labs.pixelflow               D  Screenshot detected: /storage/emulated/0/Pictures/Screenshots/Screenshot_20251217-164043_System UI.png
2025-12-17 16:40:44.317 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  New screenshot detected: /storage/emulated/0/Pictures/Screenshots/Screenshot_20251217-164043_System UI.png
2025-12-17 16:40:44.317 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Screenshot file exists, size: 457595 bytes
2025-12-17 16:40:44.365 20080-20080 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:40:44.368 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Broadcast sent for screenshot detection: /storage/emulated/0/Pictures/Screenshots/Screenshot_20251217-164043_System UI.png
2025-12-17 16:40:44.368 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Showing floating bubble for screenshot
2025-12-17 16:40:44.398 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Floating bubble shown successfully
2025-12-17 16:40:44.398 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  New screenshot detected: /storage/emulated/0/Pictures/Screenshots/Screenshot_20251217-164043_System UI.png
2025-12-17 16:40:44.399 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Screenshot file exists, size: 457595 bytes
2025-12-17 16:40:44.445 20080-20080 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:40:44.448 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Broadcast sent for screenshot detection: /storage/emulated/0/Pictures/Screenshots/Screenshot_20251217-164043_System UI.png
2025-12-17 16:40:44.448 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Showing floating bubble for screenshot
2025-12-17 16:40:44.461 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Floating bubble shown successfully
2025-12-17 16:40:44.462 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Content change detected: content://media/external/images/media/1000004923
2025-12-17 16:40:44.462 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Content change detected: content://media/external/images/media/1000004923
2025-12-17 16:40:44.462 20080-20382 ViewBasedF...bleService com.aks_labs.pixelflow               D  Checking if URI is a screenshot: content://media/external/images/media/1000004923
2025-12-17 16:40:44.462 20080-20382 ViewBasedF...bleService com.aks_labs.pixelflow               D  Querying for images added after: 1765969834
2025-12-17 16:40:44.462 20080-20080 ScreenshotReceiver      com.aks_labs.pixelflow               D  Received broadcast: com.aks_labs.pixelflow.SCREENSHOT_DETECTED
2025-12-17 16:40:44.462 20080-20080 ScreenshotReceiver      com.aks_labs.pixelflow               D  Screenshot detected: /storage/emulated/0/Pictures/Screenshots/Screenshot_20251217-164043_System UI.png
2025-12-17 16:40:44.463 20080-20383 ViewBasedF...bleService com.aks_labs.pixelflow               D  Checking if URI is a screenshot: content://media/external/images/media/1000004923
2025-12-17 16:40:44.463 20080-20383 ViewBasedF...bleService com.aks_labs.pixelflow               D  Querying for images added after: 1765969834
2025-12-17 16:40:44.475 20080-20382 ViewBasedF...bleService com.aks_labs.pixelflow               D  Query returned 1 results
2025-12-17 16:40:44.475 20080-20382 ViewBasedF...bleService com.aks_labs.pixelflow               D  Found image: Screenshot_20251217-164043_System UI.png at /storage/emulated/0/Pictures/Screenshots/Screenshot_20251217-164043_System UI.png
2025-12-17 16:40:44.476 20080-20382 ViewBasedF...bleService com.aks_labs.pixelflow               D  Is screenshot by name: true, File exists: true, Already processed: false
2025-12-17 16:40:44.476 20080-20382 ViewBasedF...bleService com.aks_labs.pixelflow               D  Screenshot detected: /storage/emulated/0/Pictures/Screenshots/Screenshot_20251217-164043_System UI.png
2025-12-17 16:40:44.479 20080-20383 ViewBasedF...bleService com.aks_labs.pixelflow               D  Query returned 1 results
2025-12-17 16:40:44.479 20080-20383 ViewBasedF...bleService com.aks_labs.pixelflow               D  Found image: Screenshot_20251217-164043_System UI.png at /storage/emulated/0/Pictures/Screenshots/Screenshot_20251217-164043_System UI.png
2025-12-17 16:40:44.479 20080-20383 ViewBasedF...bleService com.aks_labs.pixelflow               D  Is screenshot by name: true, File exists: true, Already processed: false
2025-12-17 16:40:44.480 20080-20383 ViewBasedF...bleService com.aks_labs.pixelflow               D  Screenshot detected: /storage/emulated/0/Pictures/Screenshots/Screenshot_20251217-164043_System UI.png
2025-12-17 16:40:44.499 20080-20080 ScreenshotReceiver      com.aks_labs.pixelflow               D  Received broadcast: com.aks_labs.pixelflow.SCREENSHOT_DETECTED
2025-12-17 16:40:44.499 20080-20080 ScreenshotReceiver      com.aks_labs.pixelflow               D  Screenshot detected: /storage/emulated/0/Pictures/Screenshots/Screenshot_20251217-164043_System UI.png
2025-12-17 16:40:44.503 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  New screenshot detected: /storage/emulated/0/Pictures/Screenshots/Screenshot_20251217-164043_System UI.png
2025-12-17 16:40:44.504 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Screenshot file exists, size: 457595 bytes
2025-12-17 16:40:44.547 20080-20080 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:40:44.549 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Broadcast sent for screenshot detection: /storage/emulated/0/Pictures/Screenshots/Screenshot_20251217-164043_System UI.png
2025-12-17 16:40:44.549 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Showing floating bubble for screenshot
2025-12-17 16:40:44.551 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Bubble removed
2025-12-17 16:40:44.565 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Floating bubble shown successfully
2025-12-17 16:40:44.565 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  New screenshot detected: /storage/emulated/0/Pictures/Screenshots/Screenshot_20251217-164043_System UI.png
2025-12-17 16:40:44.566 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Screenshot file exists, size: 457595 bytes
2025-12-17 16:40:44.609 20080-20080 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:40:44.612 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Broadcast sent for screenshot detection: /storage/emulated/0/Pictures/Screenshots/Screenshot_20251217-164043_System UI.png
2025-12-17 16:40:44.612 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Showing floating bubble for screenshot
2025-12-17 16:40:44.613 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Bubble removed
2025-12-17 16:40:44.628 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Floating bubble shown successfully
2025-12-17 16:40:44.629 20080-20080 ScreenshotReceiver      com.aks_labs.pixelflow               D  Received broadcast: com.aks_labs.pixelflow.SCREENSHOT_DETECTED
2025-12-17 16:40:44.629 20080-20080 ScreenshotReceiver      com.aks_labs.pixelflow               D  Screenshot detected: /storage/emulated/0/Pictures/Screenshots/Screenshot_20251217-164043_System UI.png
2025-12-17 16:40:44.629 20080-20080 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  sendCancelIfRunning: isInProgress=false callback=android.view.ViewRootImpl$$ExternalSyntheticLambda13@1d0c25e
2025-12-17 16:40:44.686 20080-20080 ScreenshotReceiver      com.aks_labs.pixelflow               D  Received broadcast: com.aks_labs.pixelflow.SCREENSHOT_DETECTED
2025-12-17 16:40:44.687 20080-20080 ScreenshotReceiver      com.aks_labs.pixelflow               D  Screenshot detected: /storage/emulated/0/Pictures/Screenshots/Screenshot_20251217-164043_System UI.png
2025-12-17 16:40:44.687 20080-20080 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  sendCancelIfRunning: isInProgress=false callback=android.view.ViewRootImpl$$ExternalSyntheticLambda13@4240e37
2025-12-17 16:40:45.334 20080-20080 BootReceiver            com.aks_labs.pixelflow               D  Service started successfully after boot
2025-12-17 16:40:45.334 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Service onStartCommand called with action: START_ON_BOOT
2025-12-17 16:40:45.829 20080-20080 InsetsController        com.aks_labs.pixelflow               D  hide(ime(), fromIme=false)
2025-12-17 16:40:45.829 20080-20080 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:efbb607b: onCancelled at PHASE_CLIENT_ALREADY_HIDDEN
2025-12-17 16:40:48.413 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Showing drag zones - START
2025-12-17 16:40:48.417 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Refreshed 9 folders from SharedPrefsManager
2025-12-17 16:40:48.417 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Folders count: 9
2025-12-17 16:40:48.417 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Hiding drag zones - START
2025-12-17 16:40:48.417 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  No drag zones view to remove
2025-12-17 16:40:48.418 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Drag zones hidden successfully
2025-12-17 16:40:48.434 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Drag zones added to window manager
2025-12-17 16:40:48.434 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Showing drag zones - COMPLETE
2025-12-17 16:40:52.551 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Hiding drag zones - START
2025-12-17 16:40:52.552 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Drag zones removed from window manager
2025-12-17 16:40:52.553 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Drag zones hidden successfully
2025-12-17 16:40:52.567 20080-20080 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  sendCancelIfRunning: isInProgress=false callback=android.view.ViewRootImpl$$ExternalSyntheticLambda13@d71f3cc
2025-12-17 16:40:53.229 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Showing drag zones - START
2025-12-17 16:40:53.234 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Refreshed 9 folders from SharedPrefsManager
2025-12-17 16:40:53.234 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Folders count: 9
2025-12-17 16:40:53.234 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Hiding drag zones - START
2025-12-17 16:40:53.234 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  No drag zones view to remove
2025-12-17 16:40:53.234 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Drag zones hidden successfully
2025-12-17 16:40:53.248 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Drag zones added to window manager
2025-12-17 16:40:53.248 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Showing drag zones - COMPLETE
2025-12-17 16:40:53.907 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Hiding drag zones - START
2025-12-17 16:40:53.909 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Drag zones removed from window manager
2025-12-17 16:40:53.909 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Drag zones hidden successfully
2025-12-17 16:40:53.918 20080-20080 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  sendCancelIfRunning: isInProgress=false callback=android.view.ViewRootImpl$$ExternalSyntheticLambda13@3b2e9ce
2025-12-17 16:40:56.713 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Showing drag zones - START
2025-12-17 16:40:56.717 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Refreshed 9 folders from SharedPrefsManager
2025-12-17 16:40:56.717 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Folders count: 9
2025-12-17 16:40:56.717 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Hiding drag zones - START
2025-12-17 16:40:56.717 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  No drag zones view to remove
2025-12-17 16:40:56.717 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Drag zones hidden successfully
2025-12-17 16:40:56.733 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Drag zones added to window manager
2025-12-17 16:40:56.733 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Showing drag zones - COMPLETE
2025-12-17 16:41:01.378 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Screenshot dropped in folder: 5
2025-12-17 16:41:01.378 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Moving screenshot to folder: Memes
2025-12-17 16:41:01.382 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Added to processed screenshots: Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:01.776 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Screenshot copied to folder: Memes at /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:01.811 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Original screenshot deleted successfully
2025-12-17 16:41:01.817 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Broadcast sent for screenshot moved: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png to folder: Memes
2025-12-17 16:41:01.819 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Bubble removed
2025-12-17 16:41:01.819 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Hiding drag zones - START
2025-12-17 16:41:01.820 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Drag zones removed from window manager
2025-12-17 16:41:01.820 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Drag zones hidden successfully
2025-12-17 16:41:01.828 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Content change detected: content://media/external/images/media/1000004924
2025-12-17 16:41:01.828 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Content change detected: content://media/external/images/media/1000004924
2025-12-17 16:41:01.828 20080-20698 ViewBasedF...bleService com.aks_labs.pixelflow               D  Checking if URI is a screenshot: content://media/external/images/media/1000004924
2025-12-17 16:41:01.828 20080-20698 ViewBasedF...bleService com.aks_labs.pixelflow               D  Querying for images added after: 1765969851
2025-12-17 16:41:01.828 20080-20699 ViewBasedF...bleService com.aks_labs.pixelflow               D  Checking if URI is a screenshot: content://media/external/images/media/1000004924
2025-12-17 16:41:01.829 20080-20699 ViewBasedF...bleService com.aks_labs.pixelflow               D  Querying for images added after: 1765969851
2025-12-17 16:41:01.830 20080-20697 MediaScannerConnection  com.aks_labs.pixelflow               D  Scanned /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png to content://media/external_primary/images/media/1000004924
2025-12-17 16:41:01.838 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Content change detected: content://media/external/images/media/1000004923
2025-12-17 16:41:01.838 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Content change detected: content://media/external/images/media/1000004923
2025-12-17 16:41:01.838 20080-20700 ViewBasedF...bleService com.aks_labs.pixelflow               D  Checking if URI is a screenshot: content://media/external/images/media/1000004923
2025-12-17 16:41:01.838 20080-20700 ViewBasedF...bleService com.aks_labs.pixelflow               D  Querying for images added after: 1765969851
2025-12-17 16:41:01.839 20080-20701 ViewBasedF...bleService com.aks_labs.pixelflow               D  Checking if URI is a screenshot: content://media/external/images/media/1000004923
2025-12-17 16:41:01.839 20080-20701 ViewBasedF...bleService com.aks_labs.pixelflow               D  Querying for images added after: 1765969851
2025-12-17 16:41:01.839 20080-20080 ScreenshotReceiver      com.aks_labs.pixelflow               D  Received broadcast: com.aks_labs.pixelflow.SCREENSHOT_MOVED
2025-12-17 16:41:01.839 20080-20080 ScreenshotReceiver      com.aks_labs.pixelflow               D  Screenshot moved: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png to folder: 5
2025-12-17 16:41:01.840 20080-20080 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  sendCancelIfRunning: isInProgress=false callback=android.view.ViewRootImpl$$ExternalSyntheticLambda13@347f40
2025-12-17 16:41:01.846 20080-20080 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  sendCancelIfRunning: isInProgress=false callback=android.view.ViewRootImpl$$ExternalSyntheticLambda13@19b6c00
2025-12-17 16:41:01.852 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Content change detected: content://media/external/images/media/1000004924
2025-12-17 16:41:01.853 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Content change detected: content://media/external/images/media/1000004924
2025-12-17 16:41:01.853 20080-20703 ViewBasedF...bleService com.aks_labs.pixelflow               D  Checking if URI is a screenshot: content://media/external/images/media/1000004924
2025-12-17 16:41:01.853 20080-20703 ViewBasedF...bleService com.aks_labs.pixelflow               D  Querying for images added after: 1765969851
2025-12-17 16:41:01.853 20080-20702 ViewBasedF...bleService com.aks_labs.pixelflow               D  Checking if URI is a screenshot: content://media/external/images/media/1000004924
2025-12-17 16:41:01.853 20080-20702 ViewBasedF...bleService com.aks_labs.pixelflow               D  Querying for images added after: 1765969851
2025-12-17 16:41:01.868 20080-20701 ViewBasedF...bleService com.aks_labs.pixelflow               D  Query returned 1 results
2025-12-17 16:41:01.868 20080-20701 ViewBasedF...bleService com.aks_labs.pixelflow               D  Found image: Screenshot_20251217-164043_System UI.png at /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:01.869 20080-20701 ViewBasedF...bleService com.aks_labs.pixelflow               D  Is screenshot by name: true, File exists: true, Already processed: true
2025-12-17 16:41:01.869 20080-20701 ViewBasedF...bleService com.aks_labs.pixelflow               D  Skipping already processed screenshot: Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:01.882 20080-20702 ViewBasedF...bleService com.aks_labs.pixelflow               D  Query returned 1 results
2025-12-17 16:41:01.882 20080-20702 ViewBasedF...bleService com.aks_labs.pixelflow               D  Found image: Screenshot_20251217-164043_System UI.png at /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:01.883 20080-20702 ViewBasedF...bleService com.aks_labs.pixelflow               D  Is screenshot by name: true, File exists: true, Already processed: true
2025-12-17 16:41:01.883 20080-20702 ViewBasedF...bleService com.aks_labs.pixelflow               D  Skipping already processed screenshot: Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:01.891 20080-20703 ViewBasedF...bleService com.aks_labs.pixelflow               D  Query returned 1 results
2025-12-17 16:41:01.892 20080-20703 ViewBasedF...bleService com.aks_labs.pixelflow               D  Found image: Screenshot_20251217-164043_System UI.png at /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:01.892 20080-20703 ViewBasedF...bleService com.aks_labs.pixelflow               D  Is screenshot by name: true, File exists: true, Already processed: false
2025-12-17 16:41:01.892 20080-20703 ViewBasedF...bleService com.aks_labs.pixelflow               D  Screenshot detected: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:01.893 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  New screenshot detected: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:01.893 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Screenshot file exists, size: 457595 bytes
2025-12-17 16:41:01.908 20080-20699 ViewBasedF...bleService com.aks_labs.pixelflow               D  Query returned 1 results
2025-12-17 16:41:01.909 20080-20699 ViewBasedF...bleService com.aks_labs.pixelflow               D  Found image: Screenshot_20251217-164043_System UI.png at /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:01.909 20080-20700 ViewBasedF...bleService com.aks_labs.pixelflow               D  Query returned 1 results
2025-12-17 16:41:01.909 20080-20700 ViewBasedF...bleService com.aks_labs.pixelflow               D  Found image: Screenshot_20251217-164043_System UI.png at /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:01.909 20080-20700 ViewBasedF...bleService com.aks_labs.pixelflow               D  Is screenshot by name: true, File exists: true, Already processed: false
2025-12-17 16:41:01.909 20080-20700 ViewBasedF...bleService com.aks_labs.pixelflow               D  Screenshot detected: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:01.910 20080-20699 ViewBasedF...bleService com.aks_labs.pixelflow               D  Is screenshot by name: true, File exists: true, Already processed: true
2025-12-17 16:41:01.910 20080-20699 ViewBasedF...bleService com.aks_labs.pixelflow               D  Skipping already processed screenshot: Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:01.912 20080-20698 ViewBasedF...bleService com.aks_labs.pixelflow               D  Query returned 1 results
2025-12-17 16:41:01.913 20080-20698 ViewBasedF...bleService com.aks_labs.pixelflow               D  Found image: Screenshot_20251217-164043_System UI.png at /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:01.913 20080-20698 ViewBasedF...bleService com.aks_labs.pixelflow               D  Is screenshot by name: true, File exists: true, Already processed: false
2025-12-17 16:41:01.913 20080-20698 ViewBasedF...bleService com.aks_labs.pixelflow               D  Screenshot detected: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:01.957 20080-20080 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:41:01.959 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Broadcast sent for screenshot detection: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:01.959 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Showing floating bubble for screenshot
2025-12-17 16:41:01.961 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Bubble removed
2025-12-17 16:41:01.978 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Floating bubble shown successfully
2025-12-17 16:41:01.978 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  New screenshot detected: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:01.978 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Screenshot file exists, size: 457595 bytes
2025-12-17 16:41:02.022 20080-20080 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:41:02.025 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Broadcast sent for screenshot detection: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:02.025 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Showing floating bubble for screenshot
2025-12-17 16:41:02.026 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Bubble removed
2025-12-17 16:41:02.043 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Floating bubble shown successfully
2025-12-17 16:41:02.044 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  New screenshot detected: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:02.044 20080-20697 MediaScannerConnection  com.aks_labs.pixelflow               D  Scanned /storage/emulated/0/Pictures/Screenshots/Screenshot_20251217-164043_System UI.png to null
2025-12-17 16:41:02.045 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Screenshot file exists, size: 457595 bytes
2025-12-17 16:41:02.089 20080-20080 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:41:02.093 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Broadcast sent for screenshot detection: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:02.093 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Showing floating bubble for screenshot
2025-12-17 16:41:02.094 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Bubble removed
2025-12-17 16:41:02.110 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Floating bubble shown successfully
2025-12-17 16:41:02.110 20080-20080 ScreenshotReceiver      com.aks_labs.pixelflow               D  Received broadcast: com.aks_labs.pixelflow.SCREENSHOT_DETECTED
2025-12-17 16:41:02.110 20080-20080 ScreenshotReceiver      com.aks_labs.pixelflow               D  Screenshot detected: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:02.111 20080-20080 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  sendCancelIfRunning: isInProgress=false callback=android.view.ViewRootImpl$$ExternalSyntheticLambda13@9f9e3b1
2025-12-17 16:41:02.177 20080-20080 ScreenshotReceiver      com.aks_labs.pixelflow               D  Received broadcast: com.aks_labs.pixelflow.SCREENSHOT_DETECTED
2025-12-17 16:41:02.177 20080-20080 ScreenshotReceiver      com.aks_labs.pixelflow               D  Screenshot detected: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:02.177 20080-20080 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  sendCancelIfRunning: isInProgress=false callback=android.view.ViewRootImpl$$ExternalSyntheticLambda13@6ef92f4
2025-12-17 16:41:02.183 20080-20080 ScreenshotReceiver      com.aks_labs.pixelflow               D  Received broadcast: com.aks_labs.pixelflow.SCREENSHOT_DETECTED
2025-12-17 16:41:02.183 20080-20080 ScreenshotReceiver      com.aks_labs.pixelflow               D  Screenshot detected: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:02.183 20080-20080 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  sendCancelIfRunning: isInProgress=false callback=android.view.ViewRootImpl$$ExternalSyntheticLambda13@1c5f7de
2025-12-17 16:41:02.997 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Showing drag zones - START
2025-12-17 16:41:03.003 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Refreshed 9 folders from SharedPrefsManager
2025-12-17 16:41:03.003 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Folders count: 9
2025-12-17 16:41:03.003 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Hiding drag zones - START
2025-12-17 16:41:03.003 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  No drag zones view to remove
2025-12-17 16:41:03.003 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Drag zones hidden successfully
2025-12-17 16:41:03.021 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Drag zones added to window manager
2025-12-17 16:41:03.021 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Showing drag zones - COMPLETE
2025-12-17 16:41:04.319 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Screenshot dropped in folder: 5
2025-12-17 16:41:04.320 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Moving screenshot to folder: Memes
2025-12-17 16:41:04.322 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Added to processed screenshots: Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:04.639 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Screenshot copied to folder: Memes at /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:04.641 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Bubble removed
2025-12-17 16:41:04.641 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Hiding drag zones - START
2025-12-17 16:41:04.642 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Drag zones removed from window manager
2025-12-17 16:41:04.642 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Drag zones hidden successfully
2025-12-17 16:41:04.652 20080-20080 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  sendCancelIfRunning: isInProgress=false callback=android.view.ViewRootImpl$$ExternalSyntheticLambda13@fb1a578
2025-12-17 16:41:04.659 20080-20080 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  sendCancelIfRunning: isInProgress=false callback=android.view.ViewRootImpl$$ExternalSyntheticLambda13@e379b54
2025-12-17 16:41:04.665 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Content change detected: content://media/external/images/media/1000004924
2025-12-17 16:41:04.665 20080-20080 ViewBasedF...bleService com.aks_labs.pixelflow               D  Content change detected: content://media/external/images/media/1000004924
2025-12-17 16:41:04.666 20080-20717 ViewBasedF...bleService com.aks_labs.pixelflow               D  Checking if URI is a screenshot: content://media/external/images/media/1000004924
2025-12-17 16:41:04.666 20080-20717 ViewBasedF...bleService com.aks_labs.pixelflow               D  Querying for images added after: 1765969854
2025-12-17 16:41:04.666 20080-20718 ViewBasedF...bleService com.aks_labs.pixelflow               D  Checking if URI is a screenshot: content://media/external/images/media/1000004924
2025-12-17 16:41:04.666 20080-20718 ViewBasedF...bleService com.aks_labs.pixelflow               D  Querying for images added after: 1765969854
2025-12-17 16:41:04.667 20080-20697 MediaScannerConnection  com.aks_labs.pixelflow               D  Scanned /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png to content://media/external_primary/images/media/1000004924
2025-12-17 16:41:04.712 20080-20717 ViewBasedF...bleService com.aks_labs.pixelflow               D  Query returned 1 results
2025-12-17 16:41:04.712 20080-20717 ViewBasedF...bleService com.aks_labs.pixelflow               D  Found image: Screenshot_20251217-164043_System UI.png at /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:04.712 20080-20718 ViewBasedF...bleService com.aks_labs.pixelflow               D  Query returned 1 results
2025-12-17 16:41:04.712 20080-20718 ViewBasedF...bleService com.aks_labs.pixelflow               D  Found image: Screenshot_20251217-164043_System UI.png at /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:04.712 20080-20717 ViewBasedF...bleService com.aks_labs.pixelflow               D  Is screenshot by name: true, File exists: true, Already processed: true
2025-12-17 16:41:04.712 20080-20717 ViewBasedF...bleService com.aks_labs.pixelflow               D  Skipping already processed screenshot: Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:04.713 20080-20718 ViewBasedF...bleService com.aks_labs.pixelflow               D  Is screenshot by name: true, File exists: true, Already processed: true
2025-12-17 16:41:04.713 20080-20718 ViewBasedF...bleService com.aks_labs.pixelflow               D  Skipping already processed screenshot: Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:06.730 20080-20194 HWUI                    com.aks_labs.pixelflow               D  --- Failed to create image decoder with message 'unimplemented'
2025-12-17 16:41:06.731 20080-20194 HWUI                    com.aks_labs.pixelflow               D  --- Failed to create image decoder with message 'unimplemented'
2025-12-17 16:41:08.787 20080-20080 InsetsController        com.aks_labs.pixelflow               D  hide(ime(), fromIme=false)
2025-12-17 16:41:08.787 20080-20080 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:4a636aeb: onCancelled at PHASE_CLIENT_ALREADY_HIDDEN
2025-12-17 16:41:10.136 20080-20080 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  sendCancelIfRunning: isInProgress=false callback=android.view.ViewRootImpl$$ExternalSyntheticLambda13@cb7a01c
2025-12-17 16:41:10.140 20080-20134 HWUI                    com.aks_labs.pixelflow               D  endAllActiveAnimators on 0x727c3f2190 (UnprojectedRipple) with handle 0x728c3b8f40
2025-12-17 16:41:10.283 20080-20080 InsetsController        com.aks_labs.pixelflow               D  hide(ime(), fromIme=false)
2025-12-17 16:41:10.283 20080-20080 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:b31a7c59: onCancelled at PHASE_CLIENT_ALREADY_HIDDEN
2025-12-17 16:41:11.163 20080-20194 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Starting refresh for 9 albums
2025-12-17 16:41:11.176 20080-20194 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Posts' (id=1): found 1 thumbnails
2025-12-17 16:41:11.176 20080-20194 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Posts': /storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png
2025-12-17 16:41:11.182 20080-20194 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Docs' (id=2): found 1 thumbnails
2025-12-17 16:41:11.182 20080-20194 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Docs': /storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png
2025-12-17 16:41:11.189 20080-20194 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Chats' (id=3): found 1 thumbnails
2025-12-17 16:41:11.190 20080-20194 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Chats': /storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png
2025-12-17 16:41:11.201 20080-20194 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Payments' (id=4): found 1 thumbnails
2025-12-17 16:41:11.201 20080-20194 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Payments': /storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png
2025-12-17 16:41:11.218 20080-20194 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Memes' (id=5): found 1 thumbnails
2025-12-17 16:41:11.218 20080-20194 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Memes': /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:11.227 20080-20194 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Tweets' (id=6): found 1 thumbnails
2025-12-17 16:41:11.227 20080-20194 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Tweets': /storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251217-163103_PixelFlow.png
2025-12-17 16:41:11.241 20080-20194 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Quotes' (id=7): found 1 thumbnails
2025-12-17 16:41:11.241 20080-20194 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Quotes': /storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png
2025-12-17 16:41:11.245 20080-20194 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Messages' (id=8): found 1 thumbnails
2025-12-17 16:41:11.246 20080-20194 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Messages': /storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png
2025-12-17 16:41:11.255 20080-20199 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Starting refresh for 9 albums
2025-12-17 16:41:11.264 20080-20199 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Posts' (id=1): found 1 thumbnails
2025-12-17 16:41:11.264 20080-20199 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Posts': /storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png
2025-12-17 16:41:11.269 20080-20199 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Docs' (id=2): found 1 thumbnails
2025-12-17 16:41:11.269 20080-20199 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Docs': /storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png
2025-12-17 16:41:11.271 20080-20194 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Unsorted' (id=9): found 1 thumbnails
2025-12-17 16:41:11.272 20080-20194 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Unsorted': /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968952395.jpg
2025-12-17 16:41:11.272 20080-20194 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Refresh complete. Total thumbnails: 9
2025-12-17 16:41:11.272 20080-20194 FolderScreen            com.aks_labs.pixelflow               D  After refresh - albumToThumbnailMapping size: 9
2025-12-17 16:41:11.272 20080-20194 FolderScreen            com.aks_labs.pixelflow               D  Album Posts (id=1): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png
2025-12-17 16:41:11.272 20080-20194 FolderScreen            com.aks_labs.pixelflow               D  Album Docs (id=2): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png
2025-12-17 16:41:11.272 20080-20194 FolderScreen            com.aks_labs.pixelflow               D  Album Chats (id=3): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png
2025-12-17 16:41:11.273 20080-20194 FolderScreen            com.aks_labs.pixelflow               D  Album Payments (id=4): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png
2025-12-17 16:41:11.273 20080-20194 FolderScreen            com.aks_labs.pixelflow               D  Album Memes (id=5): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:11.273 20080-20194 FolderScreen            com.aks_labs.pixelflow               D  Album Tweets (id=6): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251217-163103_PixelFlow.png
2025-12-17 16:41:11.273 20080-20194 FolderScreen            com.aks_labs.pixelflow               D  Album Quotes (id=7): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png
2025-12-17 16:41:11.273 20080-20194 FolderScreen            com.aks_labs.pixelflow               D  Album Messages (id=8): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png
2025-12-17 16:41:11.273 20080-20194 FolderScreen            com.aks_labs.pixelflow               D  Album Unsorted (id=9): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968952395.jpg
2025-12-17 16:41:11.274 20080-20199 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Chats' (id=3): found 1 thumbnails
2025-12-17 16:41:11.274 20080-20199 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Chats': /storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png
2025-12-17 16:41:11.278 20080-20199 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Payments' (id=4): found 1 thumbnails
2025-12-17 16:41:11.278 20080-20199 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Payments': /storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png
2025-12-17 16:41:11.287 20080-20199 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Memes' (id=5): found 1 thumbnails
2025-12-17 16:41:11.287 20080-20199 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Memes': /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:11.292 20080-20199 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Tweets' (id=6): found 1 thumbnails
2025-12-17 16:41:11.292 20080-20199 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Tweets': /storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251217-163103_PixelFlow.png
2025-12-17 16:41:11.297 20080-20199 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Quotes' (id=7): found 1 thumbnails
2025-12-17 16:41:11.297 20080-20199 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Quotes': /storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png
2025-12-17 16:41:11.299 20080-20199 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Messages' (id=8): found 1 thumbnails
2025-12-17 16:41:11.299 20080-20199 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Messages': /storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png
2025-12-17 16:41:11.312 20080-20199 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Unsorted' (id=9): found 1 thumbnails
2025-12-17 16:41:11.312 20080-20199 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Unsorted': /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968952395.jpg
2025-12-17 16:41:11.312 20080-20199 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Refresh complete. Total thumbnails: 9
2025-12-17 16:41:11.312 20080-20199 FolderScreen            com.aks_labs.pixelflow               D  After refresh - albumToThumbnailMapping size: 9
2025-12-17 16:41:11.312 20080-20199 FolderScreen            com.aks_labs.pixelflow               D  Album Posts (id=1): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png
2025-12-17 16:41:11.312 20080-20199 FolderScreen            com.aks_labs.pixelflow               D  Album Docs (id=2): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png
2025-12-17 16:41:11.312 20080-20199 FolderScreen            com.aks_labs.pixelflow               D  Album Chats (id=3): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png
2025-12-17 16:41:11.313 20080-20199 FolderScreen            com.aks_labs.pixelflow               D  Album Payments (id=4): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png
2025-12-17 16:41:11.313 20080-20199 FolderScreen            com.aks_labs.pixelflow               D  Album Memes (id=5): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:11.313 20080-20199 FolderScreen            com.aks_labs.pixelflow               D  Album Tweets (id=6): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251217-163103_PixelFlow.png
2025-12-17 16:41:11.313 20080-20199 FolderScreen            com.aks_labs.pixelflow               D  Album Quotes (id=7): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png
2025-12-17 16:41:11.313 20080-20199 FolderScreen            com.aks_labs.pixelflow               D  Album Messages (id=8): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png
2025-12-17 16:41:11.313 20080-20199 FolderScreen            com.aks_labs.pixelflow               D  Album Unsorted (id=9): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968952395.jpg
2025-12-17 16:41:11.314 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Posts, itemId: -804332824, filePath: /storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png
2025-12-17 16:41:11.329 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png, exists: true
2025-12-17 16:41:11.347 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Docs, itemId: 1440477992, filePath: /storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png
2025-12-17 16:41:11.352 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png, exists: true
2025-12-17 16:41:11.369 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Chats, itemId: 2035652294, filePath: /storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png
2025-12-17 16:41:11.374 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png, exists: true
2025-12-17 16:41:11.392 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Payments, itemId: 2057259762, filePath: /storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png
2025-12-17 16:41:11.397 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png, exists: true
2025-12-17 16:41:11.427 20080-20194 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:41:11.432 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Memes, itemId: -2131050604, filePath: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:11.434 20080-20199 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:41:11.439 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png, exists: true
2025-12-17 16:41:11.448 20080-20260 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:41:11.455 20080-20199 HWUI                    com.aks_labs.pixelflow               D  --- Failed to create image decoder with message 'unimplemented'
2025-12-17 16:41:11.455 20080-20199 HWUI                    com.aks_labs.pixelflow               D  --- Failed to create image decoder with message 'unimplemented'
2025-12-17 16:41:11.458 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Tweets, itemId: 1384305940, filePath: /storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251217-163103_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251217-163103_PixelFlow.png
2025-12-17 16:41:11.463 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251217-163103_PixelFlow.png, exists: true
2025-12-17 16:41:11.480 20080-20193 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:41:11.480 20080-20199 HWUI                    com.aks_labs.pixelflow               D  --- Failed to create image decoder with message 'unimplemented'
2025-12-17 16:41:11.481 20080-20199 HWUI                    com.aks_labs.pixelflow               D  --- Failed to create image decoder with message 'unimplemented'
2025-12-17 16:41:11.484 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Quotes, itemId: 1719613194, filePath: /storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png
2025-12-17 16:41:11.489 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png, exists: true
2025-12-17 16:41:11.506 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Messages, itemId: 150555577, filePath: /storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png
2025-12-17 16:41:11.512 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png, exists: true
2025-12-17 16:41:11.529 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Unsorted, itemId: -1830649977, filePath: /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968952395.jpg, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968952395.jpg
2025-12-17 16:41:11.531 20080-20199 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:41:11.534 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968952395.jpg, exists: true
2025-12-17 16:41:11.629 20080-20199 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:41:11.652 20080-20193 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:41:11.702 20080-20085 _labs.pixelflow         com.aks_labs.pixelflow               I  Background concurrent copying GC freed 6203KB AllocSpace bytes, 18(368KB) LOS objects, 49% free, 8728KB/17MB, paused 124us,60us total 112.588ms
2025-12-17 16:41:11.754 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Posts, itemId: -804332824, filePath: /storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png
2025-12-17 16:41:11.757 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png, exists: true
2025-12-17 16:41:11.760 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Docs, itemId: 1440477992, filePath: /storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png
2025-12-17 16:41:11.763 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png, exists: true
2025-12-17 16:41:11.766 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Chats, itemId: 2035652294, filePath: /storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png
2025-12-17 16:41:11.768 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png, exists: true
2025-12-17 16:41:11.771 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Payments, itemId: 2057259762, filePath: /storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png
2025-12-17 16:41:11.773 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png, exists: true
2025-12-17 16:41:11.775 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Memes, itemId: -2131050604, filePath: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:11.777 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png, exists: true
2025-12-17 16:41:11.779 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Tweets, itemId: 1384305940, filePath: /storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251217-163103_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251217-163103_PixelFlow.png
2025-12-17 16:41:11.781 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251217-163103_PixelFlow.png, exists: true
2025-12-17 16:41:11.784 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Quotes, itemId: 1719613194, filePath: /storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png
2025-12-17 16:41:11.786 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png, exists: true
2025-12-17 16:41:11.788 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Messages, itemId: 150555577, filePath: /storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png
2025-12-17 16:41:11.790 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png, exists: true
2025-12-17 16:41:11.792 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Unsorted, itemId: -1830649977, filePath: /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968952395.jpg, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968952395.jpg
2025-12-17 16:41:11.793 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968952395.jpg, exists: true
2025-12-17 16:41:13.703 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Posts, itemId: -804332824, filePath: /storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png
2025-12-17 16:41:13.705 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png, exists: true
2025-12-17 16:41:13.706 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Docs, itemId: 1440477992, filePath: /storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png
2025-12-17 16:41:13.708 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png, exists: true
2025-12-17 16:41:13.709 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Chats, itemId: 2035652294, filePath: /storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png
2025-12-17 16:41:13.711 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png, exists: true
2025-12-17 16:41:13.713 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Payments, itemId: 2057259762, filePath: /storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png
2025-12-17 16:41:13.715 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png, exists: true
2025-12-17 16:41:13.717 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Memes, itemId: -2131050604, filePath: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:13.718 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png, exists: true
2025-12-17 16:41:13.720 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Tweets, itemId: 1384305940, filePath: /storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251217-163103_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251217-163103_PixelFlow.png
2025-12-17 16:41:13.722 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251217-163103_PixelFlow.png, exists: true
2025-12-17 16:41:13.724 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Quotes, itemId: 1719613194, filePath: /storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png
2025-12-17 16:41:13.727 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png, exists: true
2025-12-17 16:41:13.729 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Messages, itemId: 150555577, filePath: /storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png
2025-12-17 16:41:13.731 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png, exists: true
2025-12-17 16:41:13.733 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Unsorted, itemId: -1830649977, filePath: /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968952395.jpg, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968952395.jpg
2025-12-17 16:41:13.735 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968952395.jpg, exists: true
2025-12-17 16:41:13.889 20080-20193 HWUI                    com.aks_labs.pixelflow               D  --- Failed to create image decoder with message 'unimplemented'
2025-12-17 16:41:13.889 20080-20193 HWUI                    com.aks_labs.pixelflow               D  --- Failed to create image decoder with message 'unimplemented'
2025-12-17 16:41:13.900 20080-20193 HWUI                    com.aks_labs.pixelflow               D  --- Failed to create image decoder with message 'unimplemented'
2025-12-17 16:41:13.900 20080-20193 HWUI                    com.aks_labs.pixelflow               D  --- Failed to create image decoder with message 'unimplemented'
2025-12-17 16:41:13.912 20080-20193 HWUI                    com.aks_labs.pixelflow               D  --- Failed to create image decoder with message 'unimplemented'
2025-12-17 16:41:13.913 20080-20193 HWUI                    com.aks_labs.pixelflow               D  --- Failed to create image decoder with message 'unimplemented'
2025-12-17 16:41:13.998 20080-20193 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:41:14.026 20080-20260 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:41:14.028 20080-20199 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:41:14.034 20080-20194 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:41:14.050 20080-20193 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:41:14.134 20080-20194 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:41:14.150 20080-20260 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:41:14.192 20080-20199 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:41:14.198 20080-20834 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:41:14.222 20080-20193 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:41:14.225 20080-20194 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:41:14.247 20080-20260 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:41:14.270 20080-20199 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:41:14.279 20080-20194 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:41:14.307 20080-20835 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-17 16:41:15.677 20080-20835 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Starting refresh for 9 albums
2025-12-17 16:41:15.681 20080-20835 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Posts' (id=1): found 1 thumbnails
2025-12-17 16:41:15.681 20080-20835 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Posts': /storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png
2025-12-17 16:41:15.685 20080-20835 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Docs' (id=2): found 1 thumbnails
2025-12-17 16:41:15.685 20080-20835 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Docs': /storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png
2025-12-17 16:41:15.688 20080-20835 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Chats' (id=3): found 1 thumbnails
2025-12-17 16:41:15.688 20080-20835 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Chats': /storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png
2025-12-17 16:41:15.694 20080-20835 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Payments' (id=4): found 1 thumbnails
2025-12-17 16:41:15.694 20080-20835 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Payments': /storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png
2025-12-17 16:41:15.706 20080-20835 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Memes' (id=5): found 1 thumbnails
2025-12-17 16:41:15.706 20080-20835 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Memes': /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:15.710 20080-20835 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Tweets' (id=6): found 1 thumbnails
2025-12-17 16:41:15.710 20080-20835 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Tweets': /storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251217-163103_PixelFlow.png
2025-12-17 16:41:15.715 20080-20835 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Quotes' (id=7): found 1 thumbnails
2025-12-17 16:41:15.716 20080-20835 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Quotes': /storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png
2025-12-17 16:41:15.718 20080-20835 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Messages' (id=8): found 1 thumbnails
2025-12-17 16:41:15.719 20080-20835 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Messages': /storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png
2025-12-17 16:41:15.727 20080-20835 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Unsorted' (id=9): found 1 thumbnails
2025-12-17 16:41:15.727 20080-20835 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Unsorted': /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968952395.jpg
2025-12-17 16:41:15.727 20080-20835 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Refresh complete. Total thumbnails: 9
2025-12-17 16:41:15.727 20080-20835 FolderScreen            com.aks_labs.pixelflow               D  After refresh - albumToThumbnailMapping size: 9
2025-12-17 16:41:15.727 20080-20835 FolderScreen            com.aks_labs.pixelflow               D  Album Posts (id=1): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png
2025-12-17 16:41:15.727 20080-20835 FolderScreen            com.aks_labs.pixelflow               D  Album Docs (id=2): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png
2025-12-17 16:41:15.727 20080-20835 FolderScreen            com.aks_labs.pixelflow               D  Album Chats (id=3): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png
2025-12-17 16:41:15.727 20080-20835 FolderScreen            com.aks_labs.pixelflow               D  Album Payments (id=4): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png
2025-12-17 16:41:15.727 20080-20835 FolderScreen            com.aks_labs.pixelflow               D  Album Memes (id=5): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:15.727 20080-20835 FolderScreen            com.aks_labs.pixelflow               D  Album Tweets (id=6): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251217-163103_PixelFlow.png
2025-12-17 16:41:15.727 20080-20835 FolderScreen            com.aks_labs.pixelflow               D  Album Quotes (id=7): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png
2025-12-17 16:41:15.727 20080-20835 FolderScreen            com.aks_labs.pixelflow               D  Album Messages (id=8): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png
2025-12-17 16:41:15.728 20080-20835 FolderScreen            com.aks_labs.pixelflow               D  Album Unsorted (id=9): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968952395.jpg
2025-12-17 16:41:15.777 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Posts, itemId: -804332824, filePath: /storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png
2025-12-17 16:41:15.782 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png, exists: true
2025-12-17 16:41:15.799 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Docs, itemId: 1440477992, filePath: /storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png
2025-12-17 16:41:15.804 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png, exists: true
2025-12-17 16:41:15.822 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Chats, itemId: 2035652294, filePath: /storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png
2025-12-17 16:41:15.827 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png, exists: true
2025-12-17 16:41:15.843 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Payments, itemId: 2057259762, filePath: /storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png
2025-12-17 16:41:15.848 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png, exists: true
2025-12-17 16:41:15.864 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Memes, itemId: -2131050604, filePath: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:15.868 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png, exists: true
2025-12-17 16:41:15.880 20080-20835 HWUI                    com.aks_labs.pixelflow               D  --- Failed to create image decoder with message 'unimplemented'
2025-12-17 16:41:15.880 20080-20835 HWUI                    com.aks_labs.pixelflow               D  --- Failed to create image decoder with message 'unimplemented'
2025-12-17 16:41:15.883 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Tweets, itemId: 1384305940, filePath: /storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251217-163103_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251217-163103_PixelFlow.png
2025-12-17 16:41:15.887 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251217-163103_PixelFlow.png, exists: true
2025-12-17 16:41:15.899 20080-20835 HWUI                    com.aks_labs.pixelflow               D  --- Failed to create image decoder with message 'unimplemented'
2025-12-17 16:41:15.899 20080-20835 HWUI                    com.aks_labs.pixelflow               D  --- Failed to create image decoder with message 'unimplemented'
2025-12-17 16:41:15.903 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Quotes, itemId: 1719613194, filePath: /storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png
2025-12-17 16:41:15.907 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png, exists: true
2025-12-17 16:41:15.923 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Messages, itemId: 150555577, filePath: /storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png
2025-12-17 16:41:15.927 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png, exists: true
2025-12-17 16:41:15.943 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Unsorted, itemId: -1830649977, filePath: /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968952395.jpg, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968952395.jpg
2025-12-17 16:41:15.947 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968952395.jpg, exists: true
2025-12-17 16:41:16.013 20080-20835 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Starting refresh for 9 albums
2025-12-17 16:41:16.017 20080-20835 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Posts' (id=1): found 1 thumbnails
2025-12-17 16:41:16.018 20080-20835 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Posts': /storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png
2025-12-17 16:41:16.021 20080-20835 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Docs' (id=2): found 1 thumbnails
2025-12-17 16:41:16.021 20080-20835 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Docs': /storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png
2025-12-17 16:41:16.024 20080-20835 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Chats' (id=3): found 1 thumbnails
2025-12-17 16:41:16.024 20080-20835 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Chats': /storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png
2025-12-17 16:41:16.028 20080-20835 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Payments' (id=4): found 1 thumbnails
2025-12-17 16:41:16.029 20080-20835 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Payments': /storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png
2025-12-17 16:41:16.038 20080-20835 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Memes' (id=5): found 1 thumbnails
2025-12-17 16:41:16.038 20080-20835 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Memes': /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:16.043 20080-20835 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Tweets' (id=6): found 1 thumbnails
2025-12-17 16:41:16.043 20080-20835 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Tweets': /storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251217-163103_PixelFlow.png
2025-12-17 16:41:16.048 20080-20835 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Quotes' (id=7): found 1 thumbnails
2025-12-17 16:41:16.048 20080-20835 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Quotes': /storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png
2025-12-17 16:41:16.051 20080-20835 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Messages' (id=8): found 1 thumbnails
2025-12-17 16:41:16.051 20080-20835 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Messages': /storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png
2025-12-17 16:41:16.055 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Posts, itemId: -804332824, filePath: /storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png
2025-12-17 16:41:16.056 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png, exists: true
2025-12-17 16:41:16.058 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Docs, itemId: 1440477992, filePath: /storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png
2025-12-17 16:41:16.060 20080-20835 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Folder 'Unsorted' (id=9): found 1 thumbnails
2025-12-17 16:41:16.060 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png, exists: true
2025-12-17 16:41:16.060 20080-20835 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Stored thumbnail for 'Unsorted': /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968952395.jpg
2025-12-17 16:41:16.060 20080-20835 refreshAlbumsAsync      com.aks_labs.pixelflow               D  Refresh complete. Total thumbnails: 9
2025-12-17 16:41:16.060 20080-20835 FolderScreen            com.aks_labs.pixelflow               D  After refresh - albumToThumbnailMapping size: 9
2025-12-17 16:41:16.060 20080-20835 FolderScreen            com.aks_labs.pixelflow               D  Album Posts (id=1): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png
2025-12-17 16:41:16.060 20080-20835 FolderScreen            com.aks_labs.pixelflow               D  Album Docs (id=2): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png
2025-12-17 16:41:16.060 20080-20835 FolderScreen            com.aks_labs.pixelflow               D  Album Chats (id=3): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png
2025-12-17 16:41:16.060 20080-20835 FolderScreen            com.aks_labs.pixelflow               D  Album Payments (id=4): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png
2025-12-17 16:41:16.060 20080-20835 FolderScreen            com.aks_labs.pixelflow               D  Album Memes (id=5): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:16.060 20080-20835 FolderScreen            com.aks_labs.pixelflow               D  Album Tweets (id=6): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251217-163103_PixelFlow.png
2025-12-17 16:41:16.060 20080-20835 FolderScreen            com.aks_labs.pixelflow               D  Album Quotes (id=7): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png
2025-12-17 16:41:16.060 20080-20835 FolderScreen            com.aks_labs.pixelflow               D  Album Messages (id=8): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png
2025-12-17 16:41:16.060 20080-20835 FolderScreen            com.aks_labs.pixelflow               D  Album Unsorted (id=9): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968952395.jpg
2025-12-17 16:41:16.061 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Chats, itemId: 2035652294, filePath: /storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png
2025-12-17 16:41:16.063 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png, exists: true
2025-12-17 16:41:16.064 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Payments, itemId: 2057259762, filePath: /storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png
2025-12-17 16:41:16.066 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png, exists: true
2025-12-17 16:41:16.067 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Memes, itemId: -2131050604, filePath: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:16.070 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png, exists: true
2025-12-17 16:41:16.071 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Tweets, itemId: 1384305940, filePath: /storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251217-163103_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251217-163103_PixelFlow.png
2025-12-17 16:41:16.073 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251217-163103_PixelFlow.png, exists: true
2025-12-17 16:41:16.074 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Quotes, itemId: 1719613194, filePath: /storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png
2025-12-17 16:41:16.076 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png, exists: true
2025-12-17 16:41:16.077 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Messages, itemId: 150555577, filePath: /storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png
2025-12-17 16:41:16.079 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png, exists: true
2025-12-17 16:41:16.080 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Unsorted, itemId: -1830649977, filePath: /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968952395.jpg, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968952395.jpg
2025-12-17 16:41:16.083 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968952395.jpg, exists: true
2025-12-17 16:41:16.180 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Posts, itemId: -804332824, filePath: /storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png
2025-12-17 16:41:16.183 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Posts/Screenshot_20250506-202059_Infinity-X Launcher.png, exists: true
2025-12-17 16:41:16.185 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Docs, itemId: 1440477992, filePath: /storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png
2025-12-17 16:41:16.187 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Docs/Screenshot_20250501-093408_PixelFlow.png, exists: true
2025-12-17 16:41:16.188 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Chats, itemId: 2035652294, filePath: /storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png
2025-12-17 16:41:16.190 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Chats/Screenshot_20250508-002443_PixelFlow.png, exists: true
2025-12-17 16:41:16.192 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Payments, itemId: 2057259762, filePath: /storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png
2025-12-17 16:41:16.193 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Payments/Screenshot_20251216-130903_PixelFlow.png, exists: true
2025-12-17 16:41:16.195 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Memes, itemId: -2131050604, filePath: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png
2025-12-17 16:41:16.196 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251217-164043_System UI.png, exists: true
2025-12-17 16:41:16.198 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Tweets, itemId: 1384305940, filePath: /storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251217-163103_PixelFlow.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251217-163103_PixelFlow.png
2025-12-17 16:41:16.199 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Tweets/Screenshot_20251217-163103_PixelFlow.png, exists: true
2025-12-17 16:41:16.201 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Quotes, itemId: 1719613194, filePath: /storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png
2025-12-17 16:41:16.203 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Quotes/Screenshot_20251214-212658_Google Play Store.png, exists: true
2025-12-17 16:41:16.205 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Messages, itemId: 150555577, filePath: /storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png
2025-12-17 16:41:16.206 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Messages/Screenshot_20250806-194032_Infinity-X Launcher.png, exists: true
2025-12-17 16:41:16.207 20080-20080 AlbumGridItem           com.aks_labs.pixelflow               D  Rendering album: Unsorted, itemId: -1830649977, filePath: /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968952395.jpg, thumbnailPath: /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968952395.jpg
2025-12-17 16:41:16.209 20080-20080 GlideImage              com.aks_labs.pixelflow               D  Loading image from file: /storage/emulated/0/Pictures/PixelFlow/Unsorted/screenshot_1765968952395.jpg, exists: true
