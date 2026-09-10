package com.damtab.my_bachelor_motors.serviceTest;

import com.damtab.my_bachelor_motors.entity.ImageCar;
import com.damtab.my_bachelor_motors.entity.OldCar;
import com.damtab.my_bachelor_motors.exception.ResourceNotFoundException;
import com.damtab.my_bachelor_motors.repository.OldCarRepository;
import com.damtab.my_bachelor_motors.service.OldCarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
public class OldCarServiceTest {

    @Mock
    private OldCarRepository oldCarRepository;

    @InjectMocks
    private OldCarService oldCarService;

    private OldCar test;
    private OldCar test2;
    private List<OldCar> myListTest;

    @BeforeEach
    public void initializeImageCar() {

        test = new OldCar();
        test.setOldCarId(55L);
        test.setBrand("BMW");
        test.setModel("X2");
        test.setPrice(5000);

        test2 = new OldCar();
        test2.setOldCarId(33L);
        test2.setBrand("OPEL");
        test2.setModel("Astra");
        test2.setPrice(3000);

        myListTest = new ArrayList<>();
        myListTest.add(test);
        myListTest.add(test2);
    }

    @Test
    public void getAllOldCar_shouldReturnList() throws Exception {

        Mockito.when(oldCarRepository.findAll()).thenReturn(myListTest);

        List<OldCar> result = oldCarService.getAllOldCars();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(55L, result.get(0).getOldCarId());
        assertEquals(33L, result.get(1).getOldCarId());
    }

    @Test
    public void getOldCarById_shouldReturnOldCar_whenExist()throws Exception {

        Mockito.when(oldCarRepository.findById(55L)).thenReturn(Optional.of(test));

        Optional<OldCar> result = oldCarService.getOldCarById(55L);
        assertNotNull(result);
        assertEquals(55L, result.get().getOldCarId());
        assertEquals(5000, result.get().getPrice());

    }

    @Test
    public void getOldCarById_shouldReturnFalse_whenNotExist()throws Exception {

        Mockito.when(oldCarRepository.findById(99L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> oldCarService.getOldCarById(99L)
        );
        assertEquals("Aucune ancienne voiture trouvée", exception.getMessage());

    }

    @Test
    public void createOldCar_shouldRepositoryCallSave() {
        oldCarService.createOldCar(test2);
        Mockito.verify(oldCarRepository).save(test2);
    }

    @Test
    public void deleteOldCar_shouldReturnTrue_whenOldCarExist() {
        Mockito.when(oldCarRepository.existsById(55L)).thenReturn(true);

        Boolean result = oldCarService.deleteOldCar(55L);
        assertTrue(result);
        Mockito.verify(oldCarRepository).deleteById(55L);
    }

    @Test
    public void deleOldCar_shouldReturnFalse_whenOldCarNotExist() {
        Mockito.when(oldCarRepository.existsById(99L)).thenReturn(false);

        Boolean result = oldCarService.deleteOldCar(99L);
        assertFalse(result);
        Mockito.verify(oldCarRepository, Mockito.never()).deleteById(Mockito.anyLong());
    }
}
