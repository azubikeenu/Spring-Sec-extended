package com.azubike.ellipsis.api.server.dos;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.azubike.ellipsis.api.response.dos.HexColorsApiPaginationResponse;
import com.azubike.ellipsis.entity.HexColor;

@RestController
@RequestMapping("api/dos/v1")
@Validated
public class HexColorApi {
	private static int COLOR_SIZE = 1_000_000;

	private List<HexColor> hexColors;

	private String randomColorHex() {
		var randomInt = ThreadLocalRandom.current().nextInt(0xffffff + 1);
		return String.format("#%06x", randomInt);
	}

	public HexColorApi() {
		hexColors = IntStream.rangeClosed(1, COLOR_SIZE).boxed().parallel().map(i -> {
			HexColor hexColor = new HexColor();
			hexColor.setId(i);
			hexColor.setHexColor(randomColorHex());
			return hexColor;
		}).collect(Collectors.toList());
	}

	@GetMapping(value = "/random-colors", produces = MediaType.APPLICATION_JSON_VALUE)
	List<HexColor> getRandomHexColors() {
		return hexColors;
	}

	@GetMapping(value = "/random-colors-pagination", produces = MediaType.APPLICATION_JSON_VALUE)
	HexColorsApiPaginationResponse getPaginatedRandomHexColors(
			@RequestParam(name = "page", required = true, defaultValue = "1") int page,
			@Valid @Max(10) @Min(1) @RequestParam(name = "size", required = true, defaultValue = "10") int size) {
		int startIndex = (page - 1) * size;
		List<HexColor> subList = hexColors.subList(startIndex, startIndex + size);
		HexColorsApiPaginationResponse response = new HexColorsApiPaginationResponse();
		response.setHexColors(subList);
		response.setPage(page);
		response.setSize(size);
		response.setTotalPages((int) Math.ceil(COLOR_SIZE) / size);
		return response;

	}
}
