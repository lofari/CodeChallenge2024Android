import com.example.codechallenge.api.ContentRepository
import com.example.codechallenge.common.Constants
import com.example.codechallenge.usecase.Every10thCharacterUseCase
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
class Every10thCharacterUseCaseTest {

    @Mock
    private lateinit var repository: ContentRepository

    private lateinit var useCase: Every10thCharacterUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCase = Every10thCharacterUseCase(repository)
    }

    @Test
    fun `test every 10th character`() = runBlockingTest {
        // Given
        val content = "12345678901234567890123456789012345678901234567890"
        Mockito.`when`(repository.fetchAbout()).thenReturn(content)

        // When
        val result = useCase().first()

        // Then
        val expected = "0,0,0,0,0"
        assertEquals(expected, result)

        // Verify that the saveData method was called with the correct parameters
        Mockito.verify(repository).saveData(Constants.CACHE_KEY2, expected)
    }
}
