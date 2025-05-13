package com.example.tasklistqa


import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.isOff
import androidx.compose.ui.test.isOn
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.printToLog
import androidx.compose.ui.test.printToString
import androidx.compose.ui.test.swipeDown
import androidx.compose.ui.test.swipeLeft
import androidx.navigation.NavType
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.example.tasklistqa.common.Utils.localizedName
import com.example.tasklistqa.data.models.FullTaskModel
import com.example.tasklistqa.data.models.ShortTaskModel
import com.example.tasklistqa.data.models.TaskModel
import com.example.tasklistqa.data.models.TaskPriority
import com.example.tasklistqa.data.models.TaskStatus
import com.example.tasklistqa.presentation.CreateTaskScreen
import com.example.tasklistqa.presentation.CreateTaskState
import com.example.tasklistqa.presentation.FullTaskScreen
import com.example.tasklistqa.presentation.TaskContentState
import com.example.tasklistqa.presentation.TaskScreen
import com.example.tasklistqa.presentation.TaskScreenState
import com.example.tasklistqa.presentation.components.dropdown_menu.FilterState
import com.example.tasklistqa.presentation.route.CreateTaskScreen
import com.example.tasklistqa.presentation.route.TasksScreen
import com.example.tasklistqa.presentation.viewModel.TaskCreationViewModel
import com.example.tasklistqa.presentation.viewModel.TaskDetailsViewModel
import com.example.tasklistqa.presentation.viewModel.TaskListViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.stopKoin
import org.koin.core.parameter.parametersOf
import org.robolectric.ParameterizedRobolectricTestRunner
import org.robolectric.RobolectricTestRunner

@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseTaskScreenTest {
    protected lateinit var viewModel: TaskListViewModel
    protected lateinit var createViewModel: TaskCreationViewModel
    private lateinit var taskDetailsViewModel: TaskDetailsViewModel
    lateinit var navController: TestNavHostController
    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = mockk(relaxed = true)
        createViewModel = mockk(relaxed = true)
        taskDetailsViewModel = mockk(relaxed = true)
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        every { viewModel.filterState } returns MutableStateFlow(FilterState())
        every { createViewModel.screenState } returns MutableStateFlow(
            CreateTaskState.Content(
                TaskModel(
                    name = "",
                    description = "",
                    deadline = "",
                    priority = null
                )
            )
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        stopKoin()
    }

    protected fun setSuccessState(tasks: List<ShortTaskModel>) {
        every { viewModel.screenState } returns MutableStateFlow(TaskScreenState.Success(tasks))
    }
}

@RunWith(RobolectricTestRunner::class)
class TaskScreenBasicDisplayTest : BaseTaskScreenTest() {
    @Test
    fun taskScreen_displaysTasksCorrectly() = runTest {
        setSuccessState(
            listOf(
                ShortTaskModel(
                    name = "task 1",
                    id = "1",
                    status = TaskStatus.LATE,
                    deadline = "2025-05-07",
                    priority = TaskPriority.MEDIUM
                )
            )
        )

        composeTestRule.setContent {
            MaterialTheme {
                TaskScreen(
                    viewModel = viewModel,
                    toCreationScreen = {},
                    toFullTaskScreen = {}
                )
            }
        }

        composeTestRule.onNodeWithTag("task 1").assertIsDisplayed()
        composeTestRule.onNode(
            useUnmergedTree = true,
            matcher = hasTestTag("taskDeadline_1")
        ).assertIsDisplayed()
    }

    @Test
    fun taskScreen_displaysEmptyState() = runTest {
        every { viewModel.screenState } returns MutableStateFlow(TaskScreenState.Empty)

        composeTestRule.setContent {
            MaterialTheme {
                TaskScreen(
                    viewModel = viewModel,
                    toCreationScreen = {},
                    toFullTaskScreen = {}
                )
            }
        }

        composeTestRule.onNodeWithTag("emptyStub").assertIsDisplayed()
    }

    @Test
    fun taskScreen_displaysErrorState() = runTest {
        val errorMessage = "Test error message"
        every { viewModel.screenState } returns MutableStateFlow(TaskScreenState.Error(errorMessage))

        composeTestRule.setContent {
            MaterialTheme {
                TaskScreen(
                    viewModel = viewModel,
                    toCreationScreen = {},
                    toFullTaskScreen = {}
                )
            }
        }
        composeTestRule.onNode(useUnmergedTree = true, matcher = hasTestTag("errorDialog"))
            .assertIsDisplayed()
        composeTestRule.onNode(useUnmergedTree = true, matcher = hasTestTag("errorMessage"))
            .assertIsDisplayed().assertTextEquals(errorMessage)
    }

    @Test
    fun taskScreen_displayLoadingState() {
        every { viewModel.screenState } returns MutableStateFlow(TaskScreenState.Loading)

        composeTestRule.setContent {
            MaterialTheme {
                TaskScreen(
                    viewModel = viewModel,
                    toCreationScreen = {},
                    toFullTaskScreen = {}
                )
            }
        }
        composeTestRule.onNode(useUnmergedTree = true, matcher = hasTestTag("loading"))
            .assertIsDisplayed()
    }
}

@RunWith(RobolectricTestRunner::class)
class TaskScreenInteractionTest : BaseTaskScreenTest() {
    @Test
    fun taskScreen_swipeToDelete_removesTask() = runTest {
        setSuccessState(
            listOf(
                ShortTaskModel(
                    name = "task 1",
                    id = "1",
                    status = TaskStatus.LATE,
                    deadline = "2025-05-07",
                    priority = TaskPriority.MEDIUM
                ),
                ShortTaskModel(
                    name = "task 2",
                    id = "2",
                    status = TaskStatus.ACTIVE,
                    deadline = "2025-05-09",
                    priority = TaskPriority.MEDIUM
                )
            )
        )

        composeTestRule.setContent {
            MaterialTheme {
                TaskScreen(
                    viewModel = viewModel,
                    toCreationScreen = {},
                    toFullTaskScreen = {}
                )
            }
        }

        coEvery { viewModel.deleteTask("1") } answers {
            setSuccessState(
                listOf(
                    ShortTaskModel(
                        name = "task 2",
                        id = "2",
                        status = TaskStatus.LATE,
                        deadline = "2025-05-08",
                        priority = TaskPriority.MEDIUM
                    )
                )
            )
        }

        composeTestRule.onNodeWithText("task 1").assertIsDisplayed()

        composeTestRule.onNodeWithText("task 1")
            .performTouchInput {
                val width = right - left
                swipeLeft(
                    startX = width - 10f,
                    endX = 10f,
                    durationMillis = 1000
                )
            }

        composeTestRule.waitForIdle()
        verify { viewModel.deleteTask("1") }
    }

    @Test
    fun taskScreen_clickFAB_andInputDataInCreationScreen() = runTest {
        setSuccessState(emptyList())

        navController.navigatorProvider.addNavigator(
            ComposeNavigator()
        )

        composeTestRule.setContent {
            MaterialTheme {
                NavHost(navController, startDestination = TasksScreen) {
                    composable<TasksScreen> {
                        TaskScreen(
                            viewModel = viewModel,
                            toCreationScreen = { navController.navigate(CreateTaskScreen) },
                            toFullTaskScreen = {}
                        )
                    }
                    composable<CreateTaskScreen> {
                        CreateTaskScreen(
                            onBackClick = { navController.popBackStack() },
                            viewModel = createViewModel
                        )
                    }
                }
            }
        }

        val currentState = MutableStateFlow<CreateTaskState>(
            CreateTaskState.Content(
                TaskModel(name = "", description = "", deadline = "", priority = null)
            )
        )

        coEvery { createViewModel.screenState } returns currentState

        every { createViewModel.changeTaskModel(any()) } answers {
            val task = firstArg<TaskModel>()
            currentState.value = CreateTaskState.Content(task)
        }

        composeTestRule.onNodeWithTag("addButton").performClick()
        composeTestRule.waitForIdle()

        val testTaskName = "Test Task"
        val testTaskDescription = "Test Description"
        val testPriority = TaskPriority.HIGH

        composeTestRule.onNodeWithTag("nameInput")
            .performTextInput(testTaskName)

        composeTestRule.onNodeWithTag("descriptionInput")
            .performTextInput(testTaskDescription)

        composeTestRule.onNodeWithTag("nameInput")
            .assertTextContains(testTaskName)
        composeTestRule.onNodeWithTag("descriptionInput")
            .assertTextContains(testTaskDescription)

        composeTestRule.onNodeWithTag("priorityButton").performClick()


        val updatedTask = TaskModel(
            name = testTaskName,
            description = testTaskDescription,
            deadline = "08.05.2025",
            priority = testPriority
        )
        createViewModel.changeTaskModel(updatedTask)

        composeTestRule.onNode(useUnmergedTree = true, matcher = hasTestTag("deadlineText"))
            .assertTextContains("08.05.2025")

        composeTestRule.onNode(useUnmergedTree = true, matcher = hasTestTag("priorityText"))
            .assertTextContains(testPriority.localizedName())
    }


    @Test
    fun taskScreen_clickOnTaskCard_opensFullTaskScreenWithCorrectDetails() = runTest {
        val testTaskId = "1"
        val testTask = ShortTaskModel(
            id = testTaskId,
            name = "Test Task",
            deadline = "2025-05-08",
            priority = TaskPriority.MEDIUM,
            status = TaskStatus.ACTIVE
        )

        setSuccessState(listOf(testTask))

        navController.navigatorProvider.addNavigator(ComposeNavigator())

        val fullTaskState = MutableStateFlow<TaskContentState>(
            TaskContentState.Success(
                FullTaskModel(
                    id = testTaskId,
                    name = testTask.name,
                    description = "Description",
                    deadline = testTask.deadline,
                    createDate = "2025-05-01",
                    updateDate = "2025-05-07",
                    priority = testTask.priority,
                    status = TaskStatus.ACTIVE
                )
            )
        )

        val mockViewModel = mockk<TaskDetailsViewModel>(relaxed = true)
        every { mockViewModel.screenState } returns fullTaskState

        composeTestRule.setContent {
            MaterialTheme {
                NavHost(navController, startDestination = TasksScreen) {
                    composable<TasksScreen> {
                        TaskScreen(
                            viewModel = viewModel,
                            toCreationScreen = {},
                            toFullTaskScreen = { taskId ->
                                navController.navigate("full_task/$taskId")
                            }
                        )
                    }
                    composable(
                        route = "full_task/{Id}",
                        arguments = listOf(navArgument("Id") { type = NavType.StringType })
                    ) {
                        FullTaskScreen(
                            onBackAction = { navController.navigateUp() },
                            viewModel = mockViewModel
                        )
                    }
                }
            }
        }

        composeTestRule.onNodeWithTag(testTask.name).performClick()

        composeTestRule.waitUntil {
            navController.currentBackStackEntry?.destination?.route?.startsWith("full_task/") == true
        }

        composeTestRule.onNodeWithTag("taskDetailsName").assertTextContains("Test Task")
        composeTestRule.onNodeWithTag("taskDetailsDesc").assertTextContains("Description")
        composeTestRule.onNode(useUnmergedTree = true, matcher = hasTestTag("deadlineText"))
            .assertTextContains("08.05.2025")
    }

    @Test
    fun taskDetailsScreen_editFields_updatesViewModelState() = runTest {
        val taskId = "1"
        val initialTask = FullTaskModel(
            id = taskId,
            name = "Old Task",
            description = "Old Description",
            deadline = "2025-05-08",
            createDate = "2025-05-01",
            updateDate = "2025-05-07",
            priority = TaskPriority.LOW,
            status = TaskStatus.ACTIVE
        )
        val currentState = MutableStateFlow<TaskContentState>(
            TaskContentState.Success(initialTask)
        )

        val mockViewModel = mockk<TaskDetailsViewModel>(relaxed = true)
        every { mockViewModel.screenState } returns currentState
        every { mockViewModel.editTask(any()) } answers {
            val updated = it.invocation.args[0] as FullTaskModel
            currentState.value = TaskContentState.Success(updated)
        }

        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.navigatorProvider.addNavigator(ComposeNavigator())

        composeTestRule.setContent {
            MaterialTheme {
                NavHost(navController, startDestination = "full_task/$taskId") {
                    composable(
                        route = "full_task/{Id}",
                        arguments = listOf(navArgument("Id") { type = NavType.StringType })
                    ) {
                        FullTaskScreen(
                            onBackAction = {},
                            viewModel = mockViewModel
                        )
                    }
                }
            }
        }
        composeTestRule.waitForIdle()

        println(composeTestRule.onRoot(useUnmergedTree = true).printToString())
        composeTestRule.onNodeWithTag("taskDetailsName").assertTextContains("Old Task")

        val newName = "Updated Task"
        val newDesc = "Updated Description"
        val newDeadline = "2025-05-20"
        val newPriority = TaskPriority.HIGH

        composeTestRule.onNodeWithTag("taskDetailsName").performTextClearance()
        composeTestRule.onNodeWithTag("taskDetailsName").performTextInput(newName)

        composeTestRule.onNodeWithTag("taskDetailsDesc").performTextClearance()
        composeTestRule.onNodeWithTag("taskDetailsDesc").performTextInput(newDesc)
        composeTestRule.onNodeWithTag("lazyColumn").performScrollToIndex(5)
        composeTestRule.waitForIdle()
        composeTestRule.onNode(matcher = hasTestTag("priorityButton"), useUnmergedTree = true)
            .performClick()
        composeTestRule.onNode(matcher = hasTestTag(newPriority.name), useUnmergedTree = true)
            .performClick()

        mockViewModel.editTask(
            initialTask.copy(
                priority = newPriority,
                name = newName,
                description = newDesc,
                deadline = newDeadline
            )
        )
        currentState.value = TaskContentState.Success(
            initialTask.copy(
                priority = newPriority,
                name = newName,
                description = newDesc,
                deadline = newDeadline
            )
        )


        composeTestRule.onNode(matcher = hasTestTag("priorityText"), useUnmergedTree = true)
            .assertTextContains(newPriority.localizedName())
        composeTestRule.onNodeWithTag("lazyColumn").performScrollToIndex(1)
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("taskDetailsName").assertTextContains(newName)
        composeTestRule.onNodeWithTag("taskDetailsDesc").assertTextContains(newDesc)
    }


}

@RunWith(ParameterizedRobolectricTestRunner::class)
class TaskScreenMarkAsCompletedTest : BaseTaskScreenTest() {
    @ParameterizedRobolectricTestRunner.Parameter
    lateinit var testCase: MarkAsCompletedTestCase

    companion object {
        @ParameterizedRobolectricTestRunner.Parameters(name = "{0}")
        @JvmStatic
        fun provideTestCases(): List<MarkAsCompletedTestCase> {
            val baseTask = ShortTaskModel(
                id = "2",
                name = "task 2",
                status = TaskStatus.ACTIVE,
                deadline = "2025-05-09",
                priority = TaskPriority.MEDIUM
            )

            return listOf(
                MarkAsCompletedTestCase(
                    testName = "ActiveToCompleted",
                    initialTask = baseTask,
                    expectedStatusAfterClick = TaskStatus.COMPLETED
                ),
                MarkAsCompletedTestCase(
                    testName = "CompletedToActive",
                    initialTask = baseTask.copy(status = TaskStatus.COMPLETED),
                    expectedStatusAfterClick = TaskStatus.ACTIVE
                ),
                MarkAsCompletedTestCase(
                    testName = "LateToOverdue",
                    initialTask = baseTask.copy(status = TaskStatus.LATE, deadline = "2025-05-08"),
                    expectedStatusAfterClick = TaskStatus.OVERDUE
                )
            )
        }
    }

    @Test
    fun taskScreen_markAsCompleted() = runTest {
        val initialState = MutableStateFlow(TaskScreenState.Success(listOf(testCase.initialTask)))
        val updatedTask = testCase.initialTask.copy(status = testCase.expectedStatusAfterClick)
        val updatedState = MutableStateFlow(TaskScreenState.Success(listOf(updatedTask)))

        coEvery { viewModel.screenState } returns initialState
        every { viewModel.markAsCompleted("2") } answers {
            initialState.value = updatedState.value
        }

        composeTestRule.setContent {
            MaterialTheme {
                TaskScreen(
                    viewModel = viewModel,
                    toCreationScreen = {},
                    toFullTaskScreen = {}
                )
            }
        }

        val expectedInitialState = when (testCase.initialTask.status) {
            TaskStatus.COMPLETED -> true
            TaskStatus.LATE -> true
            else -> false
        }

        composeTestRule.onNode(
            useUnmergedTree = true,
            matcher = hasTestTag("taskCheckbox_2")
        ).assert(if (expectedInitialState) isOn() else isOff())

        composeTestRule.onNode(hasTestTag("taskCheckbox_2")).performClick()

        composeTestRule.waitUntil(5000) {
            try {
                val expectedFinalState = when (testCase.expectedStatusAfterClick) {
                    TaskStatus.COMPLETED -> true
                    TaskStatus.LATE -> true
                    else -> false
                }
                composeTestRule.onNode(hasTestTag("taskCheckbox_2"))
                    .assert(if (expectedFinalState) isOn() else isOff())
                true
            } catch (e: AssertionError) {
                false
            }
        }

        verify(exactly = 1) { viewModel.markAsCompleted("2") }
    }
}


