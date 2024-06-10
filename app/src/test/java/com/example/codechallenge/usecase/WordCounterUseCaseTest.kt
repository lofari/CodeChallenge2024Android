import com.example.codechallenge.api.ContentRepository
import com.example.codechallenge.common.Constants
import com.example.codechallenge.usecase.WordCounterUseCase
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class WordCounterUseCaseTest {

    @Mock
    private lateinit var repository: ContentRepository

    private lateinit var useCase: WordCounterUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCase = WordCounterUseCase(repository)
    }

    @Test
    fun `test word counter`() = runBlockingTest {
        // Given
        val content = "Hello world! Hello again, world."
        Mockito.`when`(repository.fetchAbout()).thenReturn(content)

        // When
        val result = useCase().first()

        // Then
        val expected = mapOf(
            "hello" to 2,
            "world!" to 1,
            "again," to 1,
            "world." to 1
        )
        assertEquals(expected, result)

        // Verify that the saveData method was called with the correct parameters
        Mockito.verify(repository).saveData(
            Constants.CACHE_KEY1,
            expected.toList().sortedByDescending { it.second }.toString()
        )
    }
}
