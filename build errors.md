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
--------- beginning of system
--------- beginning of main
2025-12-16 16:10:26.531 26711-26711 ViewRootImpl            com.aks_labs.pixelflow               D  Skipping stats log for color mode
2025-12-16 16:10:26.532 26711-26711 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  OnBackInvokedCallback is not enabled for the application.
                                                                                                    Set 'android:enableOnBackInvokedCallback="true"' in the application manifest.
2025-12-16 16:10:26.596 26711-26711 InsetsController        com.aks_labs.pixelflow               D  hide(ime(), fromIme=false)
2025-12-16 16:10:26.597 26711-26711 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:4d862c59: onCancelled at PHASE_CLIENT_ALREADY_HIDDEN
2025-12-16 16:10:33.853 26711-26711 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  OnBackInvokedCallback is not enabled for the application.
                                                                                                    Set 'android:enableOnBackInvokedCallback="true"' in the application manifest.
2025-12-16 16:10:34.165 26711-26711 InsetsController        com.aks_labs.pixelflow               D  hide(ime(), fromIme=false)
2025-12-16 16:10:34.165 26711-26711 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:c86896fa: onCancelled at PHASE_CLIENT_ALREADY_HIDDEN
2025-12-16 16:10:41.496 26711-26711 Compose Focus           com.aks_labs.pixelflow               D  Owner FocusChanged(true)
2025-12-16 16:10:41.546 26711-26711 InsetsController        com.aks_labs.pixelflow               D  show(ime(), fromIme=false)
2025-12-16 16:10:41.547 26711-26711 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:5a96758b: onRequestShow at ORIGIN_CLIENT reason SHOW_SOFT_INPUT_BY_INSETS_API fromUser false
2025-12-16 16:10:41.550 26711-26711 InsetsController        com.aks_labs.pixelflow               D  Setting requestedVisibleTypes to -1 (was -9)
2025-12-16 16:10:41.766 26711-26711 RecordingIC             com.aks_labs.pixelflow               W  requestCursorUpdates is not supported
2025-12-16 16:10:42.000 26711-27050 InteractionJankMonitor  com.aks_labs.pixelflow               W  Initializing without READ_DEVICE_CONFIG permission. enabled=false, interval=1, missedFrameThreshold=3, frameTimeThreshold=64, package=com.aks_labs.pixelflow
2025-12-16 16:10:48.142 26711-26711 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  sendCancelIfRunning: isInProgress=false callback=ImeCallback=ImeOnBackInvokedCallback@208855837 Callback=android.window.IOnBackInvokedCallback$Stub$Proxy@a9655dd
2025-12-16 16:10:48.143 26711-26711 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  sendCancelIfRunning: isInProgress=false callback=android.view.ViewRootImpl$$ExternalSyntheticLambda13@2b27d0a
2025-12-16 16:10:48.254 26711-26711 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:7be12388: onRequestHide at ORIGIN_CLIENT reason HIDE_SOFT_INPUT fromUser false
2025-12-16 16:10:48.254 26711-26711 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:7be12388: onFailed at PHASE_CLIENT_VIEW_SERVED
2025-12-16 16:10:48.271 26711-26711 InsetsController        com.aks_labs.pixelflow               D  hide(ime(), fromIme=false)
2025-12-16 16:10:48.271 26711-26711 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:c6f4b5cb: onCancelled at PHASE_CLIENT_ALREADY_HIDDEN
2025-12-16 16:10:48.289 26711-26711 ImeBackDispatcher       com.aks_labs.pixelflow               E  Ime callback not found. Ignoring unregisterReceivedCallback. callbackId: 208855837
2025-12-16 16:10:51.579 26711-26711 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  OnBackInvokedCallback is not enabled for the application.
                                                                                                    Set 'android:enableOnBackInvokedCallback="true"' in the application manifest.
2025-12-16 16:10:51.825 26711-26711 InsetsController        com.aks_labs.pixelflow               D  hide(ime(), fromIme=false)
2025-12-16 16:10:51.825 26711-26711 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:e5417a45: onCancelled at PHASE_CLIENT_ALREADY_HIDDEN
2025-12-16 16:10:53.210 26711-26711 Compose Focus           com.aks_labs.pixelflow               D  Owner FocusChanged(true)
2025-12-16 16:10:53.248 26711-26711 InsetsController        com.aks_labs.pixelflow               D  show(ime(), fromIme=false)
2025-12-16 16:10:53.249 26711-26711 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:7f693d69: onRequestShow at ORIGIN_CLIENT reason SHOW_SOFT_INPUT_BY_INSETS_API fromUser false
2025-12-16 16:10:53.250 26711-26711 InsetsController        com.aks_labs.pixelflow               D  Setting requestedVisibleTypes to -1 (was -9)
2025-12-16 16:10:53.296 26711-26711 RecordingIC             com.aks_labs.pixelflow               W  requestCursorUpdates is not supported
2025-12-16 16:10:53.641 26711-26711 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:7f693d69: onShown
2025-12-16 16:11:04.004 26711-26726 _labs.pixelflow         com.aks_labs.pixelflow               I  Waiting for a blocking GC ProfileSaver
2025-12-16 16:11:04.011 26711-26726 _labs.pixelflow         com.aks_labs.pixelflow               I  WaitForGcToComplete blocked ProfileSaver on Background for 7.764ms
2025-12-16 16:11:17.800 26711-26711 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  sendCancelIfRunning: isInProgress=false callback=ImeCallback=ImeOnBackInvokedCallback@208855837 Callback=android.window.IOnBackInvokedCallback$Stub$Proxy@f867b55
2025-12-16 16:11:17.801 26711-26711 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  sendCancelIfRunning: isInProgress=false callback=android.view.ViewRootImpl$$ExternalSyntheticLambda13@902c305
2025-12-16 16:11:17.831 26711-26730 HWUI                    com.aks_labs.pixelflow               D  endAllActiveAnimators on 0x727c3a4720 (UnprojectedRipple) with handle 0x728c3e91e0
2025-12-16 16:11:17.943 26711-26711 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:4df21f2: onRequestHide at ORIGIN_CLIENT reason HIDE_SOFT_INPUT fromUser false
2025-12-16 16:11:17.943 26711-26711 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:4df21f2: onFailed at PHASE_CLIENT_VIEW_SERVED
--------- beginning of main
2025-12-16 16:10:26.531 26711-26711 ViewRootImpl            com.aks_labs.pixelflow               D  Skipping stats log for color mode
2025-12-16 16:10:26.532 26711-26711 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  OnBackInvokedCallback is not enabled for the application.
                                                                                                    Set 'android:enableOnBackInvokedCallback="true"' in the application manifest.
2025-12-16 16:10:26.596 26711-26711 InsetsController        com.aks_labs.pixelflow               D  hide(ime(), fromIme=false)
2025-12-16 16:10:26.597 26711-26711 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:4d862c59: onCancelled at PHASE_CLIENT_ALREADY_HIDDEN
2025-12-16 16:10:33.853 26711-26711 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  OnBackInvokedCallback is not enabled for the application.
                                                                                                    Set 'android:enableOnBackInvokedCallback="true"' in the application manifest.
2025-12-16 16:10:34.165 26711-26711 InsetsController        com.aks_labs.pixelflow               D  hide(ime(), fromIme=false)
2025-12-16 16:10:34.165 26711-26711 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:c86896fa: onCancelled at PHASE_CLIENT_ALREADY_HIDDEN
2025-12-16 16:10:41.496 26711-26711 Compose Focus           com.aks_labs.pixelflow               D  Owner FocusChanged(true)
2025-12-16 16:10:41.546 26711-26711 InsetsController        com.aks_labs.pixelflow               D  show(ime(), fromIme=false)
2025-12-16 16:10:41.547 26711-26711 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:5a96758b: onRequestShow at ORIGIN_CLIENT reason SHOW_SOFT_INPUT_BY_INSETS_API fromUser false
2025-12-16 16:10:41.550 26711-26711 InsetsController        com.aks_labs.pixelflow               D  Setting requestedVisibleTypes to -1 (was -9)
2025-12-16 16:10:41.766 26711-26711 RecordingIC             com.aks_labs.pixelflow               W  requestCursorUpdates is not supported
2025-12-16 16:10:42.000 26711-27050 InteractionJankMonitor  com.aks_labs.pixelflow               W  Initializing without READ_DEVICE_CONFIG permission. enabled=false, interval=1, missedFrameThreshold=3, frameTimeThreshold=64, package=com.aks_labs.pixelflow
2025-12-16 16:10:48.142 26711-26711 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  sendCancelIfRunning: isInProgress=false callback=ImeCallback=ImeOnBackInvokedCallback@208855837 Callback=android.window.IOnBackInvokedCallback$Stub$Proxy@a9655dd
2025-12-16 16:10:48.143 26711-26711 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  sendCancelIfRunning: isInProgress=false callback=android.view.ViewRootImpl$$ExternalSyntheticLambda13@2b27d0a
2025-12-16 16:10:48.254 26711-26711 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:7be12388: onRequestHide at ORIGIN_CLIENT reason HIDE_SOFT_INPUT fromUser false
2025-12-16 16:10:48.254 26711-26711 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:7be12388: onFailed at PHASE_CLIENT_VIEW_SERVED
2025-12-16 16:10:48.271 26711-26711 InsetsController        com.aks_labs.pixelflow               D  hide(ime(), fromIme=false)
2025-12-16 16:10:48.271 26711-26711 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:c6f4b5cb: onCancelled at PHASE_CLIENT_ALREADY_HIDDEN
2025-12-16 16:10:48.289 26711-26711 ImeBackDispatcher       com.aks_labs.pixelflow               E  Ime callback not found. Ignoring unregisterReceivedCallback. callbackId: 208855837
2025-12-16 16:10:51.579 26711-26711 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  OnBackInvokedCallback is not enabled for the application.
                                                                                                    Set 'android:enableOnBackInvokedCallback="true"' in the application manifest.
2025-12-16 16:10:51.825 26711-26711 InsetsController        com.aks_labs.pixelflow               D  hide(ime(), fromIme=false)
2025-12-16 16:10:51.825 26711-26711 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:e5417a45: onCancelled at PHASE_CLIENT_ALREADY_HIDDEN
2025-12-16 16:10:53.210 26711-26711 Compose Focus           com.aks_labs.pixelflow               D  Owner FocusChanged(true)
2025-12-16 16:10:53.248 26711-26711 InsetsController        com.aks_labs.pixelflow               D  show(ime(), fromIme=false)
2025-12-16 16:10:53.249 26711-26711 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:7f693d69: onRequestShow at ORIGIN_CLIENT reason SHOW_SOFT_INPUT_BY_INSETS_API fromUser false
2025-12-16 16:10:53.250 26711-26711 InsetsController        com.aks_labs.pixelflow               D  Setting requestedVisibleTypes to -1 (was -9)
2025-12-16 16:10:53.296 26711-26711 RecordingIC             com.aks_labs.pixelflow               W  requestCursorUpdates is not supported
2025-12-16 16:10:53.641 26711-26711 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:7f693d69: onShown
2025-12-16 16:11:04.004 26711-26726 _labs.pixelflow         com.aks_labs.pixelflow               I  Waiting for a blocking GC ProfileSaver
2025-12-16 16:11:04.011 26711-26726 _labs.pixelflow         com.aks_labs.pixelflow               I  WaitForGcToComplete blocked ProfileSaver on Background for 7.764ms
2025-12-16 16:11:17.800 26711-26711 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  sendCancelIfRunning: isInProgress=false callback=ImeCallback=ImeOnBackInvokedCallback@208855837 Callback=android.window.IOnBackInvokedCallback$Stub$Proxy@f867b55
2025-12-16 16:11:17.801 26711-26711 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  sendCancelIfRunning: isInProgress=false callback=android.view.ViewRootImpl$$ExternalSyntheticLambda13@902c305
2025-12-16 16:11:17.831 26711-26730 HWUI                    com.aks_labs.pixelflow               D  endAllActiveAnimators on 0x727c3a4720 (UnprojectedRipple) with handle 0x728c3e91e0
2025-12-16 16:11:17.943 26711-26711 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:4df21f2: onRequestHide at ORIGIN_CLIENT reason HIDE_SOFT_INPUT fromUser false
2025-12-16 16:11:17.943 26711-26711 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:4df21f2: onFailed at PHASE_CLIENT_VIEW_SERVED
2025-12-16 16:11:18.070 26711-26711 InsetsController        com.aks_labs.pixelflow               D  hide(ime(), fromIme=false)
2025-12-16 16:11:18.070 26711-26711 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:771df59f: onCancelled at PHASE_CLIENT_ALREADY_HIDDEN
2025-12-16 16:11:18.071 26711-26711 ImeBackDispatcher       com.aks_labs.pixelflow               E  Ime callback not found. Ignoring unregisterReceivedCallback. callbackId: 208855837
2025-12-16 16:11:30.133 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Service onDestroy called
2025-12-16 16:11:30.133 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Hiding drag zones - START
2025-12-16 16:11:30.133 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  No drag zones view to remove
2025-12-16 16:11:30.133 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Drag zones hidden successfully
2025-12-16 16:11:30.133 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Hiding action buttons
2025-12-16 16:11:30.642 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Service onCreate called
2025-12-16 16:11:30.650 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Loaded 9 folders from SharedPrefsManager
2025-12-16 16:11:30.650 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Service onStartCommand called with action: START_FROM_APP
2025-12-16 16:11:30.657 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Service starting from app
2025-12-16 16:11:30.657 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Starting screenshot detection
2025-12-16 16:11:31.362 26711-26711 SwipeToScreenShot       com.aks_labs.pixelflow               D  canceling motionEvent because of threeGesture detecting
2025-12-16 16:11:31.366 26711-26711 SwipeToScreenShot       com.aks_labs.pixelflow               D  canceling motionEvent because of threeGesture detecting
2025-12-16 16:11:31.381 26711-26711 SwipeToScreenShot       com.aks_labs.pixelflow               D  canceling motionEvent because of threeGesture detecting
2025-12-16 16:11:31.397 26711-26711 SwipeToScreenShot       com.aks_labs.pixelflow               D  canceling motionEvent because of threeGesture detecting
2025-12-16 16:11:31.414 26711-26711 SwipeToScreenShot       com.aks_labs.pixelflow               D  canceling motionEvent because of threeGesture detecting
2025-12-16 16:11:31.429 26711-26711 SwipeToScreenShot       com.aks_labs.pixelflow               D  canceling motionEvent because of threeGesture detecting
2025-12-16 16:11:31.446 26711-26711 SwipeToScreenShot       com.aks_labs.pixelflow               D  canceling motionEvent because of threeGesture detecting
2025-12-16 16:11:31.463 26711-26711 SwipeToScreenShot       com.aks_labs.pixelflow               D  canceling motionEvent because of threeGesture detecting
2025-12-16 16:11:31.480 26711-26711 SwipeToScreenShot       com.aks_labs.pixelflow               D  canceling motionEvent because of threeGesture detecting
2025-12-16 16:11:31.497 26711-26711 SwipeToScreenShot       com.aks_labs.pixelflow               D  canceling motionEvent because of threeGesture detecting
2025-12-16 16:11:31.507 26711-26711 SwipeToScreenShot       com.aks_labs.pixelflow               D  canceling motionEvent because of threeGesture detecting
2025-12-16 16:11:31.508 26711-26711 SwipeToScreenShot       com.aks_labs.pixelflow               D  canceling motionEvent because of threeGesture detecting
2025-12-16 16:11:32.115 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Content change detected: content://media/external/images/media/1000004725
2025-12-16 16:11:32.116 26711-27200 ViewBasedF...bleService com.aks_labs.pixelflow               D  Checking if URI is a screenshot: content://media/external/images/media/1000004725
2025-12-16 16:11:32.116 26711-27200 ViewBasedF...bleService com.aks_labs.pixelflow               D  Querying for images added after: 1765881682
2025-12-16 16:11:32.135 26711-27200 ViewBasedF...bleService com.aks_labs.pixelflow               D  Query returned 0 results
2025-12-16 16:11:32.580 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Content change detected: content://media/external/images/media/1000004725
2025-12-16 16:11:32.580 26711-27213 ViewBasedF...bleService com.aks_labs.pixelflow               D  Checking if URI is a screenshot: content://media/external/images/media/1000004725
2025-12-16 16:11:32.580 26711-27213 ViewBasedF...bleService com.aks_labs.pixelflow               D  Querying for images added after: 1765881682
2025-12-16 16:11:32.595 26711-27213 ViewBasedF...bleService com.aks_labs.pixelflow               D  Query returned 1 results
2025-12-16 16:11:32.595 26711-27213 ViewBasedF...bleService com.aks_labs.pixelflow               D  Found image: Screenshot_20251216-161132_PixelFlow.png at /storage/emulated/0/Pictures/Screenshots/Screenshot_20251216-161132_PixelFlow.png
2025-12-16 16:11:32.596 26711-27213 ViewBasedF...bleService com.aks_labs.pixelflow               D  Is screenshot by name: true, File exists: true, Already processed: false
2025-12-16 16:11:32.596 26711-27213 ViewBasedF...bleService com.aks_labs.pixelflow               D  Screenshot detected: /storage/emulated/0/Pictures/Screenshots/Screenshot_20251216-161132_PixelFlow.png
2025-12-16 16:11:32.596 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  New screenshot detected: /storage/emulated/0/Pictures/Screenshots/Screenshot_20251216-161132_PixelFlow.png
2025-12-16 16:11:32.597 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Screenshot file exists, size: 98146 bytes
2025-12-16 16:11:32.637 26711-26711 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-16 16:11:32.641 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Broadcast sent for screenshot detection: /storage/emulated/0/Pictures/Screenshots/Screenshot_20251216-161132_PixelFlow.png
2025-12-16 16:11:32.641 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Showing floating bubble for screenshot
2025-12-16 16:11:32.665 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Floating bubble shown successfully
2025-12-16 16:11:32.665 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Content change detected: content://media/external/images/media/1000004725
2025-12-16 16:11:32.666 26711-26711 ScreenshotReceiver      com.aks_labs.pixelflow               D  Received broadcast: com.aks_labs.pixelflow.SCREENSHOT_DETECTED
2025-12-16 16:11:32.666 26711-27214 ViewBasedF...bleService com.aks_labs.pixelflow               D  Checking if URI is a screenshot: content://media/external/images/media/1000004725
2025-12-16 16:11:32.666 26711-27214 ViewBasedF...bleService com.aks_labs.pixelflow               D  Querying for images added after: 1765881682
2025-12-16 16:11:32.667 26711-26711 ScreenshotReceiver      com.aks_labs.pixelflow               D  Screenshot detected: /storage/emulated/0/Pictures/Screenshots/Screenshot_20251216-161132_PixelFlow.png
2025-12-16 16:11:32.679 26711-27214 ViewBasedF...bleService com.aks_labs.pixelflow               D  Query returned 1 results
2025-12-16 16:11:32.680 26711-27214 ViewBasedF...bleService com.aks_labs.pixelflow               D  Found image: Screenshot_20251216-161132_PixelFlow.png at /storage/emulated/0/Pictures/Screenshots/Screenshot_20251216-161132_PixelFlow.png
2025-12-16 16:11:32.680 26711-27214 ViewBasedF...bleService com.aks_labs.pixelflow               D  Is screenshot by name: true, File exists: true, Already processed: false
2025-12-16 16:11:32.681 26711-27214 ViewBasedF...bleService com.aks_labs.pixelflow               D  Screenshot detected: /storage/emulated/0/Pictures/Screenshots/Screenshot_20251216-161132_PixelFlow.png
2025-12-16 16:11:32.684 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  New screenshot detected: /storage/emulated/0/Pictures/Screenshots/Screenshot_20251216-161132_PixelFlow.png
2025-12-16 16:11:32.686 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Screenshot file exists, size: 98146 bytes
2025-12-16 16:11:32.721 26711-26711 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-16 16:11:32.726 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Broadcast sent for screenshot detection: /storage/emulated/0/Pictures/Screenshots/Screenshot_20251216-161132_PixelFlow.png
2025-12-16 16:11:32.726 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Showing floating bubble for screenshot
2025-12-16 16:11:32.727 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Bubble removed
2025-12-16 16:11:32.741 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Floating bubble shown successfully
2025-12-16 16:11:32.741 26711-26711 ScreenshotReceiver      com.aks_labs.pixelflow               D  Received broadcast: com.aks_labs.pixelflow.SCREENSHOT_DETECTED
2025-12-16 16:11:32.741 26711-26711 ScreenshotReceiver      com.aks_labs.pixelflow               D  Screenshot detected: /storage/emulated/0/Pictures/Screenshots/Screenshot_20251216-161132_PixelFlow.png
2025-12-16 16:11:32.741 26711-26711 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  sendCancelIfRunning: isInProgress=false callback=android.view.ViewRootImpl$$ExternalSyntheticLambda13@cb94962
2025-12-16 16:11:33.697 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Showing drag zones - START
2025-12-16 16:11:33.700 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Refreshed 9 folders from SharedPrefsManager
2025-12-16 16:11:33.700 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Folders count: 9
2025-12-16 16:11:33.700 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Hiding drag zones - START
2025-12-16 16:11:33.700 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  No drag zones view to remove
2025-12-16 16:11:33.700 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Drag zones hidden successfully
2025-12-16 16:11:33.723 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Drag zones added to window manager
2025-12-16 16:11:33.723 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Showing drag zones - COMPLETE
2025-12-16 16:11:34.126 26711-26711 InsetsController        com.aks_labs.pixelflow               D  hide(ime(), fromIme=false)
2025-12-16 16:11:34.126 26711-26711 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:1eeeae33: onCancelled at PHASE_CLIENT_ALREADY_HIDDEN
2025-12-16 16:11:40.573 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Hiding drag zones - START
2025-12-16 16:11:40.575 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Drag zones removed from window manager
2025-12-16 16:11:40.575 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Drag zones hidden successfully
2025-12-16 16:11:40.590 26711-26711 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  sendCancelIfRunning: isInProgress=false callback=android.view.ViewRootImpl$$ExternalSyntheticLambda13@8ab3a10
2025-12-16 16:11:46.297 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Showing drag zones - START
2025-12-16 16:11:46.302 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Refreshed 9 folders from SharedPrefsManager
2025-12-16 16:11:46.302 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Folders count: 9
2025-12-16 16:11:46.302 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Hiding drag zones - START
2025-12-16 16:11:46.302 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  No drag zones view to remove
2025-12-16 16:11:46.302 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Drag zones hidden successfully
2025-12-16 16:11:46.317 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Drag zones added to window manager
2025-12-16 16:11:46.317 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Showing drag zones - COMPLETE
2025-12-16 16:11:48.490 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Hiding drag zones - START
2025-12-16 16:11:48.492 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Drag zones removed from window manager
2025-12-16 16:11:48.492 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Drag zones hidden successfully
2025-12-16 16:11:48.502 26711-26711 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  sendCancelIfRunning: isInProgress=false callback=android.view.ViewRootImpl$$ExternalSyntheticLambda13@7c7ef7d
2025-12-16 16:11:49.764 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Showing drag zones - START
2025-12-16 16:11:49.768 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Refreshed 9 folders from SharedPrefsManager
2025-12-16 16:11:49.768 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Folders count: 9
2025-12-16 16:11:49.768 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Hiding drag zones - START
2025-12-16 16:11:49.768 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  No drag zones view to remove
2025-12-16 16:11:49.768 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Drag zones hidden successfully
2025-12-16 16:11:49.783 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Drag zones added to window manager
2025-12-16 16:11:49.783 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Showing drag zones - COMPLETE
2025-12-16 16:11:50.433 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Screenshot dropped in folder: 5
2025-12-16 16:11:50.433 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Moving screenshot to folder: Memes
2025-12-16 16:11:50.434 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Added to processed screenshots: Screenshot_20251216-161132_PixelFlow.png
2025-12-16 16:11:50.781 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Screenshot copied to folder: Memes at /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251216-161132_PixelFlow.png
2025-12-16 16:11:50.887 26711-27307 MediaScannerConnection  com.aks_labs.pixelflow               D  Scanned /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251216-161132_PixelFlow.png to content://media/external_primary/images/media/1000004726
2025-12-16 16:11:50.896 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Original screenshot deleted successfully
2025-12-16 16:11:50.898 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Broadcast sent for screenshot moved: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251216-161132_PixelFlow.png to folder: Memes
2025-12-16 16:11:50.900 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Bubble removed
2025-12-16 16:11:50.900 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Hiding drag zones - START
2025-12-16 16:11:50.902 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Drag zones removed from window manager
2025-12-16 16:11:50.902 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Drag zones hidden successfully
2025-12-16 16:11:50.909 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Content change detected: content://media/external/images/media/1000004726
2025-12-16 16:11:50.910 26711-27312 ViewBasedF...bleService com.aks_labs.pixelflow               D  Checking if URI is a screenshot: content://media/external/images/media/1000004726
2025-12-16 16:11:50.910 26711-27312 ViewBasedF...bleService com.aks_labs.pixelflow               D  Querying for images added after: 1765881700
2025-12-16 16:11:50.914 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Content change detected: content://media/external/images/media/1000004725
2025-12-16 16:11:50.915 26711-26711 ViewBasedF...bleService com.aks_labs.pixelflow               D  Content change detected: content://media/external/images/media/1000004726
2025-12-16 16:11:50.915 26711-26711 ScreenshotReceiver      com.aks_labs.pixelflow               D  Received broadcast: com.aks_labs.pixelflow.SCREENSHOT_MOVED
2025-12-16 16:11:50.915 26711-27314 ViewBasedF...bleService com.aks_labs.pixelflow               D  Checking if URI is a screenshot: content://media/external/images/media/1000004726
2025-12-16 16:11:50.915 26711-26711 ScreenshotReceiver      com.aks_labs.pixelflow               D  Screenshot moved: /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251216-161132_PixelFlow.png to folder: 5
2025-12-16 16:11:50.915 26711-27314 ViewBasedF...bleService com.aks_labs.pixelflow               D  Querying for images added after: 1765881700
2025-12-16 16:11:50.915 26711-26711 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  sendCancelIfRunning: isInProgress=false callback=android.view.ViewRootImpl$$ExternalSyntheticLambda13@c5dd6e3
2025-12-16 16:11:50.916 26711-27313 ViewBasedF...bleService com.aks_labs.pixelflow               D  Checking if URI is a screenshot: content://media/external/images/media/1000004725
2025-12-16 16:11:50.916 26711-27313 ViewBasedF...bleService com.aks_labs.pixelflow               D  Querying for images added after: 1765881700
2025-12-16 16:11:50.925 26711-26711 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  sendCancelIfRunning: isInProgress=false callback=android.view.ViewRootImpl$$ExternalSyntheticLambda13@e3b1896
2025-12-16 16:11:50.928 26711-27312 ViewBasedF...bleService com.aks_labs.pixelflow               D  Query returned 1 results
2025-12-16 16:11:50.929 26711-27312 ViewBasedF...bleService com.aks_labs.pixelflow               D  Found image: Screenshot_20251216-161132_PixelFlow.png at /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251216-161132_PixelFlow.png
2025-12-16 16:11:50.930 26711-27312 ViewBasedF...bleService com.aks_labs.pixelflow               D  Is screenshot by name: true, File exists: true, Already processed: true
2025-12-16 16:11:50.930 26711-27312 ViewBasedF...bleService com.aks_labs.pixelflow               D  Skipping already processed screenshot: Screenshot_20251216-161132_PixelFlow.png
2025-12-16 16:11:50.932 26711-27313 ViewBasedF...bleService com.aks_labs.pixelflow               D  Query returned 1 results
2025-12-16 16:11:50.933 26711-27313 ViewBasedF...bleService com.aks_labs.pixelflow               D  Found image: Screenshot_20251216-161132_PixelFlow.png at /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251216-161132_PixelFlow.png
2025-12-16 16:11:50.933 26711-27313 ViewBasedF...bleService com.aks_labs.pixelflow               D  Is screenshot by name: true, File exists: true, Already processed: true
2025-12-16 16:11:50.933 26711-27313 ViewBasedF...bleService com.aks_labs.pixelflow               D  Skipping already processed screenshot: Screenshot_20251216-161132_PixelFlow.png
2025-12-16 16:11:50.940 26711-27314 ViewBasedF...bleService com.aks_labs.pixelflow               D  Query returned 1 results
2025-12-16 16:11:50.940 26711-27314 ViewBasedF...bleService com.aks_labs.pixelflow               D  Found image: Screenshot_20251216-161132_PixelFlow.png at /storage/emulated/0/Pictures/PixelFlow/Memes/Screenshot_20251216-161132_PixelFlow.png
2025-12-16 16:11:50.940 26711-27314 ViewBasedF...bleService com.aks_labs.pixelflow               D  Is screenshot by name: true, File exists: true, Already processed: true
2025-12-16 16:11:50.940 26711-27314 ViewBasedF...bleService com.aks_labs.pixelflow               D  Skipping already processed screenshot: Screenshot_20251216-161132_PixelFlow.png
2025-12-16 16:11:51.026 26711-27307 MediaScannerConnection  com.aks_labs.pixelflow               D  Scanned /storage/emulated/0/Pictures/Screenshots/Screenshot_20251216-161132_PixelFlow.png to null
2025-12-16 16:11:56.199 26711-26711 InsetsController        com.aks_labs.pixelflow               D  hide(ime(), fromIme=false)
2025-12-16 16:11:56.200 26711-26711 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:7acc7e01: onCancelled at PHASE_CLIENT_ALREADY_HIDDEN
2025-12-16 16:11:57.307 26711-26711 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  sendCancelIfRunning: isInProgress=false callback=android.view.ViewRootImpl$$ExternalSyntheticLambda13@7034176
2025-12-16 16:11:57.313 26711-26730 HWUI                    com.aks_labs.pixelflow               D  endAllActiveAnimators on 0x727c40f110 (UnprojectedRipple) with handle 0x728c3b3960
2025-12-16 16:11:57.365 26711-26711 InsetsController        com.aks_labs.pixelflow               D  hide(ime(), fromIme=false)
2025-12-16 16:11:57.365 26711-26711 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:879b2ad2: onCancelled at PHASE_CLIENT_ALREADY_HIDDEN
2025-12-16 16:12:27.853 26711-26711 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  sendCancelIfRunning: isInProgress=false callback=android.view.ViewRootImpl$$ExternalSyntheticLambda13@c068c65
2025-12-16 16:12:27.894 26711-26711 ViewRootImpl            com.aks_labs.pixelflow               D  Skipping stats log for color mode
---------------------------- PROCESS STARTED (27900) for package com.aks_labs.pixelflow ----------------------------
2025-12-16 16:13:33.769 27900-27900 nativeloader            com.aks_labs.pixelflow               D  Load libframework-connectivity-tiramisu-jni.so using APEX ns com_android_tethering for caller /apex/com.android.tethering/javalib/framework-connectivity-t.jar: ok
2025-12-16 16:13:33.831 27900-27900 re-initialized>         com.aks_labs.pixelflow               W  type=1400 audit(0.0:2166): avc:  granted  { execute } for  path="/data/data/com.aks_labs.pixelflow/code_cache/startup_agents/1f04feac-agent.so" dev="mmcblk0p61" ino=3113129 scontext=u:r:untrusted_app_32:s0:c34,c259,c512,c768 tcontext=u:object_r:app_data_file:s0:c34,c259,c512,c768 tclass=file app=com.aks_labs.pixelflow
2025-12-16 16:13:33.844 27900-27900 nativeloader            com.aks_labs.pixelflow               D  Load /data/user/0/com.aks_labs.pixelflow/code_cache/startup_agents/1f04feac-agent.so using system ns (caller=<unknown>): ok
2025-12-16 16:13:33.863 27900-27900 _labs.pixelflow         com.aks_labs.pixelflow               W  DexFile /data/data/com.aks_labs.pixelflow/code_cache/.studio/instruments-843f6601.jar is in boot class path but is not in a known location
2025-12-16 16:13:34.076 27900-27900 _labs.pixelflow         com.aks_labs.pixelflow               W  Redefining intrinsic method java.lang.Thread java.lang.Thread.currentThread(). This may cause the unexpected use of the original definition of java.lang.Thread java.lang.Thread.currentThread()in methods that have already been compiled.
2025-12-16 16:13:34.077 27900-27900 _labs.pixelflow         com.aks_labs.pixelflow               W  Redefining intrinsic method boolean java.lang.Thread.interrupted(). This may cause the unexpected use of the original definition of boolean java.lang.Thread.interrupted()in methods that have already been compiled.
2025-12-16 16:13:34.186 27900-27900 ziparchive              com.aks_labs.pixelflow               W  Unable to open '/data/data/com.aks_labs.pixelflow/code_cache/.overlay/base.apk/classes3.dm': No such file or directory
2025-12-16 16:13:34.190 27900-27900 ziparchive              com.aks_labs.pixelflow               W  Unable to open '/data/data/com.aks_labs.pixelflow/code_cache/.overlay/base.apk/classes7.dm': No such file or directory
2025-12-16 16:13:34.195 27900-27900 ziparchive              com.aks_labs.pixelflow               W  Unable to open '/data/data/com.aks_labs.pixelflow/code_cache/.overlay/base.apk/classes6.dm': No such file or directory
2025-12-16 16:13:34.199 27900-27900 ziparchive              com.aks_labs.pixelflow               W  Unable to open '/data/data/com.aks_labs.pixelflow/code_cache/.overlay/base.apk/classes5.dm': No such file or directory
2025-12-16 16:13:34.213 27900-27900 ziparchive              com.aks_labs.pixelflow               W  Unable to open '/data/data/com.aks_labs.pixelflow/code_cache/.overlay/base.apk/classes9.dm': No such file or directory
2025-12-16 16:13:34.230 27900-27900 ziparchive              com.aks_labs.pixelflow               W  Unable to open '/data/data/com.aks_labs.pixelflow/code_cache/.overlay/base.apk/classes4.dm': No such file or directory
2025-12-16 16:13:34.244 27900-27900 ziparchive              com.aks_labs.pixelflow               W  Unable to open '/data/app/~~MGtOcQKa8C1tcoBgzcAeDg==/com.aks_labs.pixelflow-HorZS2UZpadeJObh-BixBg==/base.dm': No such file or directory
2025-12-16 16:13:34.244 27900-27900 ziparchive              com.aks_labs.pixelflow               W  Unable to open '/data/app/~~MGtOcQKa8C1tcoBgzcAeDg==/com.aks_labs.pixelflow-HorZS2UZpadeJObh-BixBg==/base.dm': No such file or directory
2025-12-16 16:13:34.845 27900-27900 nativeloader            com.aks_labs.pixelflow               D  Configuring clns-7 for other apk /data/app/~~MGtOcQKa8C1tcoBgzcAeDg==/com.aks_labs.pixelflow-HorZS2UZpadeJObh-BixBg==/base.apk. target_sdk_version=33, uses_libraries=, library_path=/data/app/~~MGtOcQKa8C1tcoBgzcAeDg==/com.aks_labs.pixelflow-HorZS2UZpadeJObh-BixBg==/lib/arm64, permitted_path=/data:/mnt/expand:/data/user/0/com.aks_labs.pixelflow
2025-12-16 16:13:34.865 27900-27900 _labs.pixelflow         com.aks_labs.pixelflow               I  AssetManager2(0x733c3824b8) locale list changing from [] to [en-IN]
2025-12-16 16:13:34.868 27900-27900 _labs.pixelflow         com.aks_labs.pixelflow               I  AssetManager2(0x733c378558) locale list changing from [] to [en-IN]
2025-12-16 16:13:34.888 27900-27900 GraphicsEnvironment     com.aks_labs.pixelflow               V  Currently set values for:
2025-12-16 16:13:34.888 27900-27900 GraphicsEnvironment     com.aks_labs.pixelflow               V    angle_gl_driver_selection_pkgs=[com.android.angle, com.linecorp.b612.android, com.campmobile.snow, com.google.android.apps.tachyon]
2025-12-16 16:13:34.888 27900-27900 GraphicsEnvironment     com.aks_labs.pixelflow               V    angle_gl_driver_selection_values=[angle, native, native, native]
2025-12-16 16:13:34.888 27900-27900 GraphicsEnvironment     com.aks_labs.pixelflow               V  com.aks_labs.pixelflow is not listed in per-application setting
2025-12-16 16:13:34.889 27900-27900 GraphicsEnvironment     com.aks_labs.pixelflow               V  Neither updatable production driver nor prerelease driver is supported.
2025-12-16 16:13:35.029 27900-27900 ScreenshotReceiver      com.aks_labs.pixelflow               D  ScreenshotBroadcastReceiver registered
2025-12-16 16:13:35.041 27900-27900 BootReceiver            com.aks_labs.pixelflow               D  Received intent: android.intent.action.BOOT_COMPLETED
2025-12-16 16:13:35.041 27900-27900 BootReceiver            com.aks_labs.pixelflow               D  Starting FloatingBubbleService after boot or app update
2025-12-16 16:13:35.116 27900-27900 _labs.pixelflow         com.aks_labs.pixelflow               I  AssetManager2(0x733c378878) locale list changing from [] to [en-IN]
2025-12-16 16:13:35.213 27900-27900 DesktopModeFlagsUtil    com.aks_labs.pixelflow               D  Toggle override initialized to: OVERRIDE_UNSET
2025-12-16 16:13:35.268 27900-27900 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  OnBackInvokedCallback is not enabled for the application.
                                                                                                    Set 'android:enableOnBackInvokedCallback="true"' in the application manifest.
2025-12-16 16:13:35.532 27900-27900 _labs.pixelflow         com.aks_labs.pixelflow               W  Method java.lang.Object androidx.compose.runtime.snapshots.SnapshotStateMap.mutate(kotlin.jvm.functions.Function1) failed lock verification and will run slower.
                                                                                                    Common causes for lock verification issues are non-optimized dex code
                                                                                                    and incorrect proguard optimizations.
2025-12-16 16:13:35.532 27900-27900 _labs.pixelflow         com.aks_labs.pixelflow               W  Method void androidx.compose.runtime.snapshots.SnapshotStateMap.update(kotlin.jvm.functions.Function1) failed lock verification and will run slower.
2025-12-16 16:13:35.533 27900-27900 _labs.pixelflow         com.aks_labs.pixelflow               W  Method boolean androidx.compose.runtime.snapshots.SnapshotStateMap.removeIf$runtime_release(kotlin.jvm.functions.Function1) failed lock verification and will run slower.
2025-12-16 16:13:35.842 27900-27921 AdrenoGLES-0            com.aks_labs.pixelflow               I  QUALCOMM build                   : 95db91f, Ifbc588260a
                                                                                                    Build Date                       : 09/24/20
                                                                                                    OpenGL ES Shader Compiler Version: EV031.32.02.01
                                                                                                    Local Branch                     : mybrancheafe5b6d-fb5b-f1b0-b904-5cb90179c3e0
                                                                                                    Remote Branch                    : quic/gfx-adreno.lnx.1.0.r114-rel
                                                                                                    Remote Branch                    : NONE
                                                                                                    Reconstruct Branch               : NOTHING
2025-12-16 16:13:35.842 27900-27921 AdrenoGLES-0            com.aks_labs.pixelflow               I  Build Config                     : S P 10.0.7 AArch64
2025-12-16 16:13:35.842 27900-27921 AdrenoGLES-0            com.aks_labs.pixelflow               I  Driver Path                      : /vendor/lib64/egl/libGLESv2_adreno.so
2025-12-16 16:13:35.862 27900-27921 AdrenoGLES-0            com.aks_labs.pixelflow               I  PFP: 0x016ee190, ME: 0x00000000
2025-12-16 16:13:35.912 27900-27900 _labs.pixelflow         com.aks_labs.pixelflow               W  Method boolean androidx.compose.runtime.snapshots.SnapshotStateList.conditionalUpdate(kotlin.jvm.functions.Function1) failed lock verification and will run slower.
2025-12-16 16:13:35.912 27900-27900 _labs.pixelflow         com.aks_labs.pixelflow               W  Method java.lang.Object androidx.compose.runtime.snapshots.SnapshotStateList.mutate(kotlin.jvm.functions.Function1) failed lock verification and will run slower.
2025-12-16 16:13:35.912 27900-27900 _labs.pixelflow         com.aks_labs.pixelflow               W  Method void androidx.compose.runtime.snapshots.SnapshotStateList.update(kotlin.jvm.functions.Function1) failed lock verification and will run slower.
2025-12-16 16:13:35.963 27900-27937 Gralloc4                com.aks_labs.pixelflow               I  mapper 4.x is not supported
2025-12-16 16:13:35.964 27900-27937 Gralloc3                com.aks_labs.pixelflow               W  mapper 3.x is not supported
2025-12-16 16:13:35.973 27900-27937 Gralloc2                com.aks_labs.pixelflow               I  Adding additional valid usage bits: 0x8202000
2025-12-16 16:13:36.031 27900-27900 MainActivity            com.aks_labs.pixelflow               D  Starting ViewBasedFloatingBubbleService
2025-12-16 16:13:36.031 27900-27900 MainActivity            com.aks_labs.pixelflow               D  Starting foreground service on Android O+
2025-12-16 16:13:36.033 27900-27900 MainActivity            com.aks_labs.pixelflow               D  Service start requested successfully
2025-12-16 16:13:36.050 27900-27900 Choreographer           com.aks_labs.pixelflow               I  Skipped 38 frames!  The application may be doing too much work on its main thread.
2025-12-16 16:13:36.805 27900-27911 HWUI                    com.aks_labs.pixelflow               I  Davey! duration=1370ms; Flags=0, FrameTimelineVsyncId=25915493, IntendedVsync=38098069552123, Vsync=38098702879199, InputEventId=0, HandleInputStart=38098705558485, AnimationStart=38098705563693, PerformTraversalsStart=38098854758381, DrawStart=38099399815985, FrameDeadline=38098666971354, FrameInterval=38098705028224, FrameStartTime=16666502, SyncQueued=38099418464110, SyncStart=38099418536610, IssueDrawCommandsStart=38099418630724, SwapBuffers=38099436659995, FrameCompleted=38099439640672, DequeueBufferDuration=15573, QueueBufferDuration=242917, GpuCompleted=38099439640672, SwapBuffersCompleted=38099437370151, DisplayPresentTime=0, CommandSubmissionCompleted=38099436659995, 
2025-12-16 16:13:37.136 27900-27900 InsetsController        com.aks_labs.pixelflow               D  hide(ime(), fromIme=false)
2025-12-16 16:13:37.136 27900-27900 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:7049cb1f: onCancelled at PHASE_CLIENT_ALREADY_HIDDEN
2025-12-16 16:13:37.721 27900-27947 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-16 16:13:37.721 27900-27932 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-16 16:13:37.721 27900-27934 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-16 16:13:37.721 27900-27933 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-16 16:13:37.778 27900-27946 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-16 16:13:37.832 27900-27933 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-16 16:13:37.846 27900-27932 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-16 16:13:37.879 27900-27946 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-16 16:13:37.917 27900-27933 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-16 16:13:37.919 27900-27932 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-16 16:13:37.954 27900-27946 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-16 16:13:38.026 27900-27932 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-16 16:13:38.046 27900-27946 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-16 16:13:38.087 27900-27933 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-16 16:13:38.099 27900-27946 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-16 16:13:38.172 27900-27946 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-16 16:13:38.178 27900-27934 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-16 16:13:38.222 27900-27933 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-16 16:13:38.234 27900-27946 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-16 16:13:38.273 27900-27934 HWUI                    com.aks_labs.pixelflow               W  Image decoding logging dropped!
2025-12-16 16:13:38.404 27900-27912 HWUI                    com.aks_labs.pixelflow               I  Davey! duration=1242ms; Flags=0, FrameTimelineVsyncId=25917361, IntendedVsync=38099800246049, Vsync=38100016918206, InputEventId=0, HandleInputStart=38100032770463, AnimationStart=38100032773432, PerformTraversalsStart=38100107945984, DrawStart=38100108057130, FrameDeadline=38099838244007, FrameInterval=38100032306505, FrameStartTime=16667089, SyncQueued=38101029954161, SyncStart=38101030083328, IssueDrawCommandsStart=38101030334942, SwapBuffers=38101032903953, FrameCompleted=38101042395515, DequeueBufferDuration=18229, QueueBufferDuration=202760, GpuCompleted=38101042395515, SwapBuffersCompleted=38101033576036, DisplayPresentTime=0, CommandSubmissionCompleted=38101032903953, 
2025-12-16 16:13:38.594 27900-27900 Choreographer           com.aks_labs.pixelflow               I  Skipped 72 frames!  The application may be doing too much work on its main thread.
2025-12-16 16:13:38.994 27900-27900 BootReceiver            com.aks_labs.pixelflow               D  Service started successfully after boot
2025-12-16 16:13:39.003 27900-27911 HWUI                    com.aks_labs.pixelflow               I  Davey! duration=1605ms; Flags=0, FrameTimelineVsyncId=25917571, IntendedVsync=38100033567582, Vsync=38101233573942, InputEventId=0, HandleInputStart=38101249074057, AnimationStart=38101249077025, PerformTraversalsStart=38101604203484, DrawStart=38101604313484, FrameDeadline=38101071611056, FrameInterval=38101248656296, FrameStartTime=16666755, SyncQueued=38101628358015, SyncStart=38101628425359, IssueDrawCommandsStart=38101628622129, SwapBuffers=38101630509004, FrameCompleted=38101639585619, DequeueBufferDuration=16250, QueueBufferDuration=255156, GpuCompleted=38101639585619, SwapBuffersCompleted=38101631199629, DisplayPresentTime=0, CommandSubmissionCompleted=38101630509004, 
2025-12-16 16:13:39.626 27900-27900 Choreographer           com.aks_labs.pixelflow               I  Skipped 61 frames!  The application may be doing too much work on its main thread.
2025-12-16 16:13:40.101 27900-27911 HWUI                    com.aks_labs.pixelflow               I  Davey! duration=1485ms; Flags=0, FrameTimelineVsyncId=25918605, IntendedVsync=38101252968026, Vsync=38102269681256, InputEventId=379166958, HandleInputStart=38102281640775, AnimationStart=38102281643848, PerformTraversalsStart=38102691138223, DrawStart=38102691268587, FrameDeadline=38101666909483, FrameInterval=38102281044525, FrameStartTime=16667430, SyncQueued=38102724844212, SyncStart=38102724934264, IssueDrawCommandsStart=38102725183900, SwapBuffers=38102728640098, FrameCompleted=38102738767129, DequeueBufferDuration=16718, QueueBufferDuration=320416, GpuCompleted=38102738086921, SwapBuffersCompleted=38102738767129, DisplayPresentTime=0, CommandSubmissionCompleted=38102728640098, 
2025-12-16 16:13:40.285 27900-27900 ViewBasedF...bleService com.aks_labs.pixelflow               D  Service onCreate called
2025-12-16 16:13:40.294 27900-27900 ViewBasedF...bleService com.aks_labs.pixelflow               D  Loaded 9 folders from SharedPrefsManager
2025-12-16 16:13:40.295 27900-27900 ViewBasedF...bleService com.aks_labs.pixelflow               D  Service onStartCommand called with action: START_ON_BOOT
2025-12-16 16:13:40.297 27900-27912 HWUI                    com.aks_labs.pixelflow               I  Davey! duration=1676ms; Flags=1, FrameTimelineVsyncId=25918605, IntendedVsync=38101252968026, Vsync=38102269681256, InputEventId=0, HandleInputStart=38102281640775, AnimationStart=38102281643848, PerformTraversalsStart=38102691138223, DrawStart=38102915439837, FrameDeadline=38101269634692, FrameInterval=38102281044525, FrameStartTime=16666678, SyncQueued=38102927584316, SyncStart=38102927779733, IssueDrawCommandsStart=38102928195723, SwapBuffers=38102928939369, FrameCompleted=38102929828379, DequeueBufferDuration=22864, QueueBufferDuration=263646, GpuCompleted=38102929798014, SwapBuffersCompleted=38102929828379, DisplayPresentTime=0, CommandSubmissionCompleted=38102928939369, 
2025-12-16 16:13:40.432 27900-27900 Choreographer           com.aks_labs.pixelflow               I  Skipped 41 frames!  The application may be doing too much work on its main thread.
2025-12-16 16:13:40.721 27900-27911 HWUI                    com.aks_labs.pixelflow               I  Davey! duration=957ms; Flags=0, FrameTimelineVsyncId=25919561, IntendedVsync=38102399982765, Vsync=38103083151743, InputEventId=0, HandleInputStart=38103087529264, AnimationStart=38103087531973, PerformTraversalsStart=38103321425358, DrawStart=38103353099629, FrameDeadline=38102957681708, FrameInterval=38103087206764, FrameStartTime=16666728, SyncQueued=38103356016504, SyncStart=38103356147337, IssueDrawCommandsStart=38103356280775, SwapBuffers=38103356753327, FrameCompleted=38103357895514, DequeueBufferDuration=15417, QueueBufferDuration=295729, GpuCompleted=38103357524473, SwapBuffersCompleted=38103357895514, DisplayPresentTime=0, CommandSubmissionCompleted=38103356753327, 
2025-12-16 16:13:40.728 27900-27963 ProfileInstaller        com.aks_labs.pixelflow               D  Installing profile for com.aks_labs.pixelflow
2025-12-16 16:13:40.962 27900-27900 Choreographer           com.aks_labs.pixelflow               I  Skipped 30 frames!  The application may be doing too much work on its main thread.
2025-12-16 16:13:41.186 27900-27900 InsetsController        com.aks_labs.pixelflow               D  hide(ime(), fromIme=false)
2025-12-16 16:13:41.186 27900-27900 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:8b158c2: onCancelled at PHASE_CLIENT_ALREADY_HIDDEN
2025-12-16 16:13:41.186 27900-27911 HWUI                    com.aks_labs.pixelflow               I  Davey! duration=722ms; Flags=0, FrameTimelineVsyncId=25920347, IntendedVsync=38103102897773, Vsync=38103602900933, InputEventId=0, HandleInputStart=38103617479368, AnimationStart=38103617482077, PerformTraversalsStart=38103820441868, DrawStart=38103821908431, FrameDeadline=38103383152785, FrameInterval=38103616894368, FrameStartTime=16666658, SyncQueued=38103822221347, SyncStart=38103822329785, IssueDrawCommandsStart=38103822411764, SwapBuffers=38103824138691, FrameCompleted=38103825523431, DequeueBufferDuration=15989, QueueBufferDuration=233125, GpuCompleted=38103825523431, SwapBuffersCompleted=38103825009420, DisplayPresentTime=0, CommandSubmissionCompleted=38103824138691, 
2025-12-16 16:13:41.432 27900-27900 MainActivity            com.aks_labs.pixelflow               W  Service not running after start request, trying again
2025-12-16 16:13:42.854 27900-27911 HWUI                    com.aks_labs.pixelflow               I  Davey! duration=944ms; Flags=0, FrameTimelineVsyncId=25922216, IntendedVsync=38104552891892, Vsync=38104936222013, InputEventId=0, HandleInputStart=38104948536451, AnimationStart=38104948625670, PerformTraversalsStart=38105134686920, DrawStart=38105134789889, FrameDeadline=38104769561562, FrameInterval=38104948015409, FrameStartTime=16666780, SyncQueued=38105483427545, SyncStart=38105483664524, IssueDrawCommandsStart=38105483930513, SwapBuffers=38105485512961, FrameCompleted=38105497760566, DequeueBufferDuration=16666, QueueBufferDuration=233386, GpuCompleted=38105497760566, SwapBuffersCompleted=38105486021086, DisplayPresentTime=38102768558639, CommandSubmissionCompleted=38105485512961, 
2025-12-16 16:13:42.854 27900-27911 HWUI                    com.aks_labs.pixelflow               I  Davey! duration=945ms; Flags=0, FrameTimelineVsyncId=25922216, IntendedVsync=38104552891892, Vsync=38104936222013, InputEventId=512199471, HandleInputStart=38104948536451, AnimationStart=38104948625670, PerformTraversalsStart=38105134686920, DrawStart=38105484047388, FrameDeadline=38104569558558, FrameInterval=38104948015409, FrameStartTime=16666780, SyncQueued=38105487705409, SyncStart=38105487989628, IssueDrawCommandsStart=38105488065201, SwapBuffers=38105489941034, FrameCompleted=38105498592597, DequeueBufferDuration=19947, QueueBufferDuration=217083, GpuCompleted=38105498592597, SwapBuffersCompleted=38105490653326, DisplayPresentTime=0, CommandSubmissionCompleted=38105489941034, 
2025-12-16 16:13:42.882 27900-27900 Choreographer           com.aks_labs.pixelflow               I  Skipped 35 frames!  The application may be doing too much work on its main thread.
2025-12-16 16:13:42.933 27900-27905 _labs.pixelflow         com.aks_labs.pixelflow               I  Compiler allocated 4762KB to compile void com.aks_labs.pixelflow.ui.screens.ImprovedHomeScreenKt$ImprovedHomeScreen$13.invoke(androidx.compose.foundation.layout.PaddingValues, androidx.compose.runtime.Composer, int)
2025-12-16 16:13:43.069 27900-27962 HWUI                    com.aks_labs.pixelflow               I  Davey! duration=755ms; Flags=0, FrameTimelineVsyncId=25922682, IntendedVsync=38104950228054, Vsync=38105533562834, InputEventId=0, HandleInputStart=38105537302076, AnimationStart=38105537305045, PerformTraversalsStart=38105682423430, DrawStart=38105682516451, FrameDeadline=38105524225865, FrameInterval=38105536941868, FrameStartTime=16666678, SyncQueued=38105697497857, SyncStart=38105697628482, IssueDrawCommandsStart=38105697894836, SwapBuffers=38105699515409, FrameCompleted=38105705871190, DequeueBufferDuration=16875, QueueBufferDuration=243281, GpuCompleted=38105705871190, SwapBuffersCompleted=38105700512076, DisplayPresentTime=38102785226244, CommandSubmissionCompleted=38105699515409, 
2025-12-16 16:13:43.384 27900-27900 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  sendCancelIfRunning: isInProgress=false callback=android.view.ViewRootImpl$$ExternalSyntheticLambda13@c9d94b1
2025-12-16 16:13:43.391 27900-27921 HWUI                    com.aks_labs.pixelflow               D  endAllActiveAnimators on 0x727c3f34e0 (UnprojectedRipple) with handle 0x728c3f29c0
2025-12-16 16:13:43.458 27900-27900 InsetsController        com.aks_labs.pixelflow               D  hide(ime(), fromIme=false)
2025-12-16 16:13:43.458 27900-27900 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:388548b0: onCancelled at PHASE_CLIENT_ALREADY_HIDDEN
2025-12-16 16:13:46.565 27900-27906 _labs.pixelflow         com.aks_labs.pixelflow               W  Cleared Reference was only reachable from finalizer (only reported once)
2025-12-16 16:13:50.095 27900-27900 BootReceiver            com.aks_labs.pixelflow               D  Service started successfully after boot
2025-12-16 16:13:50.095 27900-27900 ViewBasedF...bleService com.aks_labs.pixelflow               D  Service onStartCommand called with action: START_ON_BOOT
2025-12-16 16:14:05.159 27900-27900 BootReceiver            com.aks_labs.pixelflow               D  Service started successfully after boot
2025-12-16 16:14:05.160 27900-27900 ViewBasedF...bleService com.aks_labs.pixelflow               D  Service onStartCommand called with action: START_ON_BOOT
2025-12-16 16:14:22.749 27900-27900 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  OnBackInvokedCallback is not enabled for the application.
                                                                                                    Set 'android:enableOnBackInvokedCallback="true"' in the application manifest.
2025-12-16 16:14:23.027 27900-27900 InsetsController        com.aks_labs.pixelflow               D  hide(ime(), fromIme=false)
2025-12-16 16:14:23.027 27900-27900 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:183f2dec: onCancelled at PHASE_CLIENT_ALREADY_HIDDEN
2025-12-16 16:14:24.852 27900-27900 WindowOnBackDispatcher  com.aks_labs.pixelflow               W  sendCancelIfRunning: isInProgress=false callback=android.view.ViewRootImpl$$ExternalSyntheticLambda13@4bdcb00
2025-12-16 16:14:24.924 27900-27900 InsetsController        com.aks_labs.pixelflow               D  hide(ime(), fromIme=false)
2025-12-16 16:14:24.925 27900-27900 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:5128eba6: onCancelled at PHASE_CLIENT_ALREADY_HIDDEN
2025-12-16 16:14:31.478 27900-27900 InsetsController        com.aks_labs.pixelflow               D  hide(ime(), fromIme=false)
2025-12-16 16:14:31.478 27900-27900 ImeTracker              com.aks_labs.pixelflow               I  com.aks_labs.pixelflow:d06346f1: onCancelled at PHASE_CLIENT_ALREADY_HIDDEN
