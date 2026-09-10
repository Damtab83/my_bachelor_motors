package com.damtab.my_bachelor_motors.serviceTest;

import com.damtab.my_bachelor_motors.entity.ImageCar;
import com.damtab.my_bachelor_motors.exception.ResourceNotFoundException;
import com.damtab.my_bachelor_motors.repository.ImageCarRepository;
import com.damtab.my_bachelor_motors.service.ImageCarService;
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

@ExtendWith(MockitoExtension.class)
public class ImageCarServiceTest {

    @Mock
    private ImageCarRepository imageCarRepository;

    @InjectMocks
    private ImageCarService imageCarService;

    private ImageCar testImageCar;
    private ImageCar test2;
    private List<ImageCar> myImagesCar;

    @BeforeEach
    public void initializeImageCar() {

        testImageCar = new ImageCar();
        testImageCar.setImageCarId(55L);
        testImageCar.setName("image_BMW");
        testImageCar.setSize(55555);

        test2 = new ImageCar();
        test2.setImageCarId(33L);
        test2.setName("image_OPEL");
        test2.setSize(333);

        myImagesCar = new ArrayList<>();
        myImagesCar.add(testImageCar);
        myImagesCar.add(test2);
    }

    @Test
    public void getAllImageCar_shouldReturnList() throws  Exception {

        Mockito.when(imageCarRepository.findAll()).thenReturn(myImagesCar);

        List<ImageCar> result = imageCarService.getAllImageCars();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(55L, result.get(0).getImageCarId());
        assertEquals(33L, result.get(1).getImageCarId());
    }

    @Test
    public void getImageCarById_shouldReturnImageCar_whenExist() {

        Mockito.when(imageCarRepository.findById(55L)).thenReturn(Optional.of(testImageCar));
        ImageCar result = imageCarService.getImageCarById(55L);
        assertNotNull(result);
        assertEquals("image_BMW", result.getName());
        assertEquals(55555, result.getSize());
    }

    @Test
    public void getImageCarById_shouldReturnFalse_whenNotExist() {

        Mockito.when(imageCarRepository.findById(99L)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> imageCarService.getImageCarById(99L)
        );

        assertEquals("Aucune image trouvée", exception.getMessage());
    }

    @Test
    public void createImageCar_shouldRepositoryCallSave() {
        imageCarService.createImageCar(test2);
        Mockito.verify(imageCarRepository).save(test2);
    }

    @Test
    public void deleteImageCar_shouldReturnTrue_whenImageCarExist() {
        Mockito.when(imageCarRepository.existsById(55L)).thenReturn(true);

        Boolean result = imageCarService.deleteImageCar(55L);
        assertTrue(result);
        Mockito.verify(imageCarRepository).deleteById(55L);
    }

    @Test
    public void deleImageCar_shouldReturnFalse_whenImageCarNotExist() {
        Mockito.when(imageCarRepository.existsById(99L)).thenReturn(false);

        Boolean result = imageCarService.deleteImageCar(99L);
        assertFalse(result);
        Mockito.verify(imageCarRepository, Mockito.never()).deleteById(Mockito.anyLong());
    }
}
