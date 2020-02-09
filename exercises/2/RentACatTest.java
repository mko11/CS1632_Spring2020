import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.mockito.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RentACatTest {

	/**
	 * Whether to test RentACatBuggy instead of RentACatImpl
	 */

	public static boolean _testRentACatBuggy = false;

	/**
	 * The test fixture for this JUnit test. Test fixture: a fixed state of a set of
	 * objects used as a baseline for running tests. The test fixture is initialized
	 * using the @Before setUp method which runs before every test case. The test
	 * fixture is removed using the @After tearDown method which runs after each
	 * test case.
	 */

	RentACat _r; // Object to test
	@Mock
	Cat _c1; // First mock cat object
	@Mock
	Cat _c2; // Second mock cat object
	@Mock
	Cat _c3; // Third mock cat object

	@Before
	public void setUp() throws Exception {
		// Turn on automatic bug injection in the Cat class, to emulate a buggy Cat.
		// Your unit tests should work regardless of these bugs.
		Cat._bugInjectionOn = true;

		// INITIALIZE THE TEST FIXTURE
		// 1. Create a new RentACat object and assign to _r
		if (_testRentACatBuggy) {
			_r = new RentACatBuggy();
		} else {
			_r = new RentACatImpl();
		}

		// 2. Create a mock Cat with ID 1 and name "Jennyanydots", assign to _c1
		Cat mockCat1 = Mockito.mock(Cat.class);
		Mockito.when(mockCat1.getId()).thenReturn(1);
		Mockito.when(mockCat1.getName()).thenReturn("Jennyanydots");
		_c1 = mockCat1;

		// 3. Create a mock Cat with ID 2 and name "Old Deuteronomy", assign to _c2
		Cat mockCat2 = Mockito.mock(Cat.class);
		Mockito.when(mockCat2.getId()).thenReturn(2);
		Mockito.when(mockCat2.getName()).thenReturn("Old Deuteronomy");
		_c2 = mockCat2;


		// 4. Create a mock Cat with ID 3 and name "Mistoffelees", assign to _c3
		Cat mockCat3 = Mockito.mock(Cat.class);
		Mockito.when(mockCat3.getId()).thenReturn(3);
		Mockito.when(mockCat3.getName()).thenReturn("Mistoffelees");
		_c3 = mockCat3;

		// Hint: You will have to stub the mocked Cats to make them behave as if the ID
		// is 1 and name is "Jennyanydots", etc.
	}

	@After
	public void tearDown() throws Exception {
		// Not necessary strictly speaking since the references will be overwritten in
		// the next setUp call anyway and Java has automatic garbage collection.
		_r = null;
		_c1 = null;
		_c2 = null;
		_c3 = null;
	}

	/**
	 * Test case for Cat getCat(int id).
	 * Preconditions: _r has no cats.
	 * Execution steps: Call getCat(2).
	 * Postconditions: Return value is null.
	 */

	@Test
	public void testGetCatNullNumCats0() {
		assertTrue(_r.getCat(2)==null);
	}

	/**
	 * Test case for Cat getCat(int id).
	 * Preconditions: _c1, _c2, and _c3 are added to _r using addCat(Cat c).
	 * Execution steps: Call getCat(2).
	 * Postconditions: Return value is not null.
	 *                 Returned cat has an ID of 2.
	 */

	@Test
	public void testGetCatNumCats3() {
		//preconditions
		_r.addCat(_c1);
		_r.addCat(_c2);
		_r.addCat(_c3);

		//execution
		Cat cat2 = _r.getCat(2);

		//Postconditions
		assertTrue(cat2 != null && cat2.getId()==2);
	}

	/**
	 * Test case for boolean catAvailable(int id).
	 * Preconditions: _r has no cats.
	 * Execution steps: Call catAvailable(2).
	 * Postconditions: Return value is false.
	 */

	@Test
	public void testCatAvailableFalseNumCats0() {
		assertFalse(_r.catAvailable(2));
	}

	/**
	 * Test case for boolean catAvailable(int id).
	 * Preconditions: _c1, _c2, and _c3 are added to _r using addCat(Cat c).
	 *                _c3 is rented.
	 *                _c1 and _c2 are not rented.
	 * Execution steps: Call catAvailable(2).
	 * Postconditions: Return value is true.
	 */

	@Test
	public void testCatAvailableTrueNumCats3() {
		//preconditions
		_r.addCat(_c1);
		_r.addCat(_c2);
		_r.addCat(_c3);
		Mockito.when(_c3.getRented()).thenReturn(true);

		//Execution
		assertTrue(_r.catAvailable(2));
	}

	/**
	 * Test case for boolean catAvailable(int id).
	 * Preconditions: _c1, _c2, and _c3 are added to _r using addCat(Cat c).
	 *                _c2 is rented.
	 *                _c1 and _c3 are not rented.
	 * Execution steps: Call catAvailable(2).
	 * Postconditions: Return value is false.
	 */

	@Test
	public void testCatAvailableFalseNumCats3() {
		//preconditions
		_r.addCat(_c1);
		_r.addCat(_c2);
		_r.addCat(_c3);
		Mockito.when(_c2.getRented()).thenReturn(true);
		//Execution
		assertFalse(_r.catAvailable(2));
	}

	/**
	 * Test case for boolean catExists(int id).
	 * Preconditions: _r has no cats.
	 * Execution steps: Call catExists(2).
	 * Postconditions: Return value is false.
	 */

	@Test
	public void testCatExistsFalseNumCats0() {
		assertFalse(_r.catExists(2));
	}

	/**
	 * Test case for boolean catExists(int id).
	 * Preconditions: _c1, _c2, and _c3 are added to _r using addCat(Cat c).
	 * Execution steps: Call catExists(2).
	 * Postconditions: Return value is true.
	 */

	@Test
	public void testCatExistsTrueNumCats3() {
		//preconditions
		_r.addCat(_c1);
		_r.addCat(_c2);
		_r.addCat(_c3);
		//Execution
		assertTrue(_r.catExists(2));
	}

	/**
	 * Test case for String listCats().
	 * Preconditions: _r has no cats.
	 * Execution steps: Call listCats().
	 * Postconditions: Return value is "".
	 */

	@Test
	public void testListCatsNumCats0() {
		assertEquals(_r.listCats(),"");
	}

	/**
	 * Test case for String listCats().
	 * Preconditions: _c1, _c2, and _c3 are added to _r using addCat(Cat c).
	 * Execution steps: Call listCats().
	 * Postconditions: Return value is "ID 1. Jennyanydots\nID 2. Old
	 *                 Deuteronomy\nID 3. Mistoffelees\n".
	 */

	@Test
	public void testListCatsNumCats3() {
		//preconditions
		_r.addCat(_c1);
		_r.addCat(_c2);
		_r.addCat(_c3);
		Mockito.when(_c1.toString()).thenReturn("ID 1. Jennyanydots");
		Mockito.when(_c2.toString()).thenReturn("ID 2. Old Deuteronomy");
		Mockito.when(_c3.toString()).thenReturn("ID 3. Mistoffelees");
		//Execution
		assertEquals(_r.listCats(),"ID 1. Jennyanydots\nID 2. Old Deuteronomy\nID 3. Mistoffelees\n");
	}

	/**
	 * Test case for boolean rentCat(int id).
	 * Preconditions: _r has no cats.
	 * Execution steps: Call rentCat(2).
	 * Postconditions: Return value is false.
	 */

	@Test
	public void testRentCatFailureNumCats0() {
		assertFalse(_r.rentCat(2));
	}

	/**
	 * Test case for boolean rentCat(int id).
	 * Preconditions: _c1, _c2, and _c3 are added to _r using addCat(Cat c).
	 *                _c2 is rented.
	 * Execution steps: Call rentCat(2).
	 * Postconditions: Return value is false.
	 *                 _c1.rentCat(), _c2.rentCat(), _c3.rentCat() are never called.
	 *
	 * Hint: See Example/NoogieTest.java in testBadgerPlayCalled method to see an
	 * example of behavior verification.
	 */

	@Test
	public void testRentCatFailureNumCats3() {
		//preconditions
		_r.addCat(_c1);
		_r.addCat(_c2);
		_r.addCat(_c3);
		Mockito.when(_c2.getRented()).thenReturn(true);
		Mockito.verify(_c1, Mockito.times(0)).rentCat();
		Mockito.verify(_c2, Mockito.times(0)).rentCat();
		Mockito.verify(_c3, Mockito.times(0)).rentCat();
		assertFalse(_r.rentCat(2));
	}

	/**
	 * Test case for boolean returnCat(int id).
	 * Preconditions: _r has no cats.
	 * Execution steps: Call returnCat(2).
	 * Postconditions: Return value is false.
	 */

	@Test
	public void testReturnCatFailureNumCats0() {
		assertFalse(_r.returnCat(2));
	}

	/**
	 * Test case for boolean returnCat(int id).
	 * Preconditions: _c1, _c2, and _c3 are added to _r using addCat(Cat c).
	 *                _c2 is rented.
	 * Execution steps: Call returnCat(2).
	 * Postconditions: Return value is true.
	 *                 _c2.returnCat() is called exactly once.
	 *                 _c1.returnCat() and _c3.returnCat are never called.
	 *
	 * Hint: See Example/NoogieTest.java in testBadgerPlayCalled method to see an
	 * example of behavior verification.
	 */

	@Test
	public void testReturnCatNumCats3() {
		//preconditions
		_r.addCat(_c1);
		_r.addCat(_c2);
		_r.addCat(_c3);
		Mockito.when(_c2.getRented()).thenReturn(true);
		assertTrue(_r.returnCat(2));
		Mockito.verify(_c1, Mockito.times(0)).returnCat();
		Mockito.verify(_c2, Mockito.times(1)).returnCat();
		Mockito.verify(_c3, Mockito.times(0)).returnCat();

	}
}



