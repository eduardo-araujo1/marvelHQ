package com.eduardo.MarvelApi.service;

import com.eduardo.MarvelApi.converter.HQConverter;
import com.eduardo.MarvelApi.dto.HQDTO;
import com.eduardo.MarvelApi.exception.HQAlreadyRegistered;
import com.eduardo.MarvelApi.exception.ResourceNotFoundException;
import com.eduardo.MarvelApi.model.Category;
import com.eduardo.MarvelApi.model.HQ;
import com.eduardo.MarvelApi.repositories.HQRepository;
import com.eduardo.MarvelApi.services.HQService;
import com.eduardo.MarvelApi.util.HQTestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.eduardo.MarvelApi.util.HQTestUtil.NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HQServiceTest {

    @Mock
    private HQRepository repository;

    @Mock
    private HQConverter converter;

    @InjectMocks
    private HQService service;

    HQDTO mockHQDto = HQTestUtil.createHQDTO();
    HQ mockEntityHQ = HQTestUtil.createHQ();

    @Test
    public void testRegisterHQ_NotRegistered() {
        when(repository.findByName(NAME)).thenReturn(Optional.empty());
        when(repository.save(any(HQ.class))).thenReturn(mockEntityHQ);
        when(converter.toModel(mockHQDto)).thenReturn(mockEntityHQ);
        when(converter.toDTO(mockEntityHQ)).thenReturn(mockHQDto);

        HQDTO result = service.registerHQ(mockHQDto);

        assertEquals(mockHQDto, result);
        verify(repository, times(1)).findByName(NAME);
        verify(repository, times(1)).save(any(HQ.class));
    }

    @Test
    public void testRegisterHQ_AlreadyRegistered() {
        when(repository.findByName(NAME)).thenThrow(HQAlreadyRegistered.class);

        assertThatThrownBy(() -> service.registerHQ(mockHQDto)).isInstanceOf(HQAlreadyRegistered.class);

        verify(repository, times(1)).findByName(NAME);
        verify(repository, never()).save(any(HQ.class));

    }

    @Test
    public void testFindAll() {
        HQ mockEntityHQ1 = HQTestUtil.createHQ();
        HQ mockEntityHQ2 = HQTestUtil.createHQ();
        List<HQ> hqList = List.of(mockEntityHQ1, mockEntityHQ2);
        Page<HQ> hqPage = new PageImpl<>(hqList, Pageable.unpaged(), hqList.size());

        when(repository.findAll(any(PageRequest.class))).thenReturn(hqPage);

        HQDTO mockHQDto1 = HQTestUtil.createHQDTO();
        HQDTO mockHQDto2 = HQTestUtil.createHQDTO();
        when(converter.toDTO(mockEntityHQ1)).thenReturn(mockHQDto1);
        when(converter.toDTO(mockEntityHQ2)).thenReturn(mockHQDto2);

        Page<HQDTO> result = service.findAll(0, 10);

        verify(repository).findAll(PageRequest.of(0, 10));
        assertThat(result).isNotNull();
    }

    @Test
    public void testFindAll_RepositoryThrowsException() {
        when(repository.findAll(any(PageRequest.class))).thenThrow(new RuntimeException("Erro ao buscar HQs"));


        assertThatThrownBy(() -> service.findAll(0, 10))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Erro ao buscar HQs");

        verify(repository).findAll(PageRequest.of(0, 10));
    }

    @Test
    public void testFindByName_HQFound() {
        when(repository.findByName(anyString())).thenReturn(Optional.of(mockEntityHQ));
        when(converter.toDTO(mockEntityHQ)).thenReturn(mockHQDto);

        HQDTO result = service.findByName(NAME);

        assertThat(result).isEqualTo(mockHQDto);
        verify(repository).findByName(NAME);
    }

    @Test
    public void testFindByName_HQNotFound() {
        when(repository.findByName(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findByName(NAME))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Infelizmente não temos esta HQ.");

        verify(repository).findByName(NAME);
    }

    @Test
    public void testFindHQsByPrice_Success() {
        HQ mockHQ1 = HQTestUtil.createHQ();
        HQ mockHQ2 = HQTestUtil.createHQ();

        List<HQ> hqList = List.of(mockHQ1, mockHQ2);
        Page<HQ> hqPage = new PageImpl<>(hqList);

        when(repository.findByPriceBetween(any(Double.class), any(Double.class), any(Pageable.class))).thenReturn(hqPage);

        HQDTO mockHQDto1 = HQTestUtil.createHQDTO();
        HQDTO mockHQDto2 = HQTestUtil.createHQDTO();

        when(converter.toDTO(mockHQ1)).thenReturn(mockHQDto1);
        when(converter.toDTO(mockHQ2)).thenReturn(mockHQDto2);

        Page<HQDTO> result = service.findHQsByPrice(10.0, 50.0, Pageable.unpaged());

        assertThat(result.getContent()).containsExactly(mockHQDto1, mockHQDto2);
    }

    @Test
    public void testFindHQsByPrice_NoHQsFound() {
        when(repository.findByPriceBetween(any(Double.class), any(Double.class), any(Pageable.class))).thenReturn(Page.empty());

        assertThatThrownBy(() -> service.findHQsByPrice(10.0, 50.0, Pageable.unpaged()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Não encontramos produtos correspondentes a seleção.");
    }

    @Test
    public void testFindHQsByCategory_Success() {
        HQ mockHQ1 = HQTestUtil.createHQ();
        HQ mockHQ2 = HQTestUtil.createHQ();

        List<HQ> hqList = List.of(mockHQ1, mockHQ2);
        Page<HQ> hqPage = new PageImpl<>(hqList);

        when(repository.findByCategory(any(Category.class), any(Pageable.class))).thenReturn(hqPage);

        HQDTO mockHQDto1 = HQTestUtil.createHQDTO();
        HQDTO mockHQDto2 = HQTestUtil.createHQDTO();

        when(converter.toDTO(mockHQ1)).thenReturn(mockHQDto1);
        when(converter.toDTO(mockHQ2)).thenReturn(mockHQDto2);

        Page<HQDTO> result = service.findHQsByCategory(Category.OUTROS, Pageable.unpaged());

        assertThat(result.getContent()).containsExactly(mockHQDto1, mockHQDto2);
    }

    @Test
    public void testFindHQsByCategory_NoHQsFound() {
        when(repository.findByCategory(any(Category.class), any(Pageable.class))).thenReturn(Page.empty());

        assertThatThrownBy(() -> service.findHQsByCategory(Category.OUTROS, Pageable.unpaged()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Nenhuma HQ foi encontrada nesta categoria.");
    }


}


