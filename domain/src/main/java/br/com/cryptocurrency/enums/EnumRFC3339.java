package br.com.cryptocurrency.enums;

import static java.util.stream.Collectors.toMap;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EnumRFC3339 {
	DEFAULT("yyyy-MM-dd'T'HH:mm:ss"), DEFAULT_Z("yyyy-MM-dd'T'HH:mm:ss'Z'"), DEFAULT_MILI("yyyy-MM-dd'T'HH:mm:ss.SSS"),
	DEFAULT_MILI_Z("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

	private static final Map<Integer, EnumRFC3339> map = Stream.of(EnumRFC3339.values())
			.collect(toMap(format -> format.getPattern().replaceAll("'", "").length(), Function.identity()));

	public static final ZoneId ZONE_ID_UTC = ZoneId.of("UTC");

	private final String pattern;

	/**
	 * 
	 * @param data
	 * @return
	 */
	public static Optional<EnumRFC3339> whichPattern(@NotNull String data) {
		Objects.requireNonNull(data);
		return Optional.ofNullable(map.get(data.length()));
	}

	/**
	 * 
	 * @param data
	 * @return
	 */
	public static Optional<LocalDateTime> whichPatterToLocalDateTime(@NotNull String data) {
		return whichPattern(data).map(enumRfc33391 -> enumRfc33391.toLocalDateTime(data));
	}

	/**
	 * 
	 * @return
	 */
	public DateTimeFormatter getFormatter() {
		return DateTimeFormatter.ofPattern(pattern).withZone(ZONE_ID_UTC);
	}

	/**
	 * 
	 * @return
	 */
	public String now() {
		return getFormatter().format(LocalDateTime.now());
	}

	/**
	 * 
	 * @param data
	 * @return
	 */
	public LocalDateTime toLocalDateTime(@NotNull String data) {
		return getFormatter().parse(Objects.requireNonNull(data), LocalDateTime::from);
	}

	/**
	 * 
	 * @param data
	 * @return
	 */
	public Optional<LocalDateTime> toLocalDateTimeSafe(@Null String data) {
		try {
			return Optional.of(toLocalDateTime(data));
		} catch (NullPointerException | DateTimeParseException e) {
			return Optional.empty();
		}
	}
}
