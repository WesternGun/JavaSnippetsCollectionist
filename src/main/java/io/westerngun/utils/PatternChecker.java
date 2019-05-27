package io.westerngun.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@UtilityClass
public class PatternChecker {
    private static final int[] COMMON_FLAGS = new int[] {
            // ignore case
            Pattern.CASE_INSENSITIVE,
            // tell compiler to match "." with any character, including line terminator
            Pattern.DOTALL};

    /**
     * Check if the content contains a substring, with flags commonly used, i.e.,
     * {@link #COMMON_FLAGS}, including {@link Pattern#CASE_INSENSITIVE}, {@link Pattern#DOTALL}.
     * See {@link #getMatcherWithFlags(String, String, int[])} for details.
     *
     * @param substring regex expression
     * @param content the text to match
     * @return if the content starts with the substring
     */
    public static boolean findWithCommonFlags(String substring, String content) {
        return findWithFlags(substring, content, COMMON_FLAGS);
    }

    public static boolean findWithFlags(String substring, String content, int[] flags) {
        Matcher matcher = getMatcherWithFlags(substring, content, flags);
        return matcher.find();
    }
    /**
     * Check if the content starts with a substring, with flags commonly used, i.e.,
     * {@link #COMMON_FLAGS}, including {@link Pattern#CASE_INSENSITIVE}, {@link Pattern#DOTALL}.
     * See {@link #getMatcherWithFlags(String, String, int[])} for details.
     *
     * @param substring regex expression
     * @param content the text to match
     * @return if the content starts with the substring
     */
    public static boolean startWithCommonFlags(String substring, String content) {
        return startsWithFlags(substring, content, COMMON_FLAGS);
    }

    public static boolean startsWithFlags(String substring, String content, int[] flags) {
        Matcher matcher = getMatcherWithFlags(substring, content, flags);
        return matcher.lookingAt();
    }
    /**
     * Match a string with a Java regex expression, with flags commonly used, i.e.,
     * {@link #COMMON_FLAGS}, including {@link Pattern#CASE_INSENSITIVE}, {@link Pattern#DOTALL}.
     * See {@link #getMatcherWithFlags(String, String, int[])} for details.
     *
     * @param regex regex expression
     * @param content the text to match
     * @return if the text matches the pattern
     */
    public static boolean matchWithCommonFlags(String regex, String content) {
        return matchWithFlags(regex, content, COMMON_FLAGS);
    }

    /**
     * Match a string with a Java regex expression, with all the flags you need to
     * tailor for specific use cases.
     * In most of the cases {@link #matchWithCommonFlags(String, String)},
     * {@link #startWithCommonFlags(String, String)} and {@link #findWithCommonFlags(String, String)}
     * should suffice.
     *
     * See {@link #getMatcherWithFlags(String, String, int[])} for details.
     * @param regex regex expression
     * @param content the text to match
     * @return if the text matches the regex
     */
    public static boolean matchWithFlags(String regex, String content, int[] flags) {
        Matcher matcher = getMatcherWithFlags(regex, content, flags);
        boolean result = false;
        while (matcher.find()) {
            result = true;
            log.debug("Find match of [{}] starting at position {}, ending at position {}" + System.lineSeparator(), matcher.group(), matcher.start(), matcher.end());
        }
        return result;
    }


    /**
     * Match a Java regex expression. Use {@link Matcher#matches()} method to get
     * exact match to the full content.
     * Java regex syntax differs with that of other languages. For example, Java uses
     * double backslashes (<code>\\</code>) to escape special characters
     * while other languages use single backslash (<code>\</code>). It means, for example,
     * to use <code>\w</code> in Java regex, the regex string is <code>\\w</code>.
     *
     * Check JavaDoc of {@link Pattern} class to know more about Java regex syntax.
     *
     * As for flags, they are bit masks to configure the Pattern compiling, such as case-insensitivity,
     * unix line mode(only takes <code>\n</code> as line terminator), multiple line mode, etc. See {@link Pattern}
     * constants fields for more details.
     * @param regex regex expression
     * @param content the text to match
     * @param flags all Pattern flags as configuration of compilation.
     * @return the matcher, for further logic (using lookingAt(), or find(), or matches())
     */
    private static Matcher getMatcherWithFlags(String regex, String content, int[] flags) {
        int allFlags = 0;
        // combine all flags by bit operation OR
        for (int i: flags) {
            allFlags = allFlags | i;
        }
        allFlags = applyUnixLineModeIfNeeded(allFlags);
        Pattern pattern = Pattern.compile(regex, allFlags); // recreate every time; don't share it
        return pattern.matcher(content);

    }

    /**
     * Method to apply {@link Pattern#UNIX_LINES} flag to the compilation flag,
     * depending on the system property "os.name".
     * UNIX line mode only takes "\n" as line terminator
     * @param flag the original flag
     * @return the original flag, or its combination with {@link Pattern#UNIX_LINES}.
     */
    private static int applyUnixLineModeIfNeeded(int flag) {
        String osName = (String) System.getProperties().get("os.name");
        boolean isUnix = OSUtils.isUnixLinux();
        log.debug("OS name is: {}, is UNIX: {}", osName, isUnix);
        return  isUnix ? (flag | Pattern.UNIX_LINES) : flag;
    }

}
