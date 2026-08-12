import { normalizeHtmlStyle } from '../normalizeHtmlStyle';

describe('normalizeHtmlStyle mention compatibility', () => {
  it('preserves extended native mention styling', () => {
    const indicator = '\uE002';
    const normalized = normalizeHtmlStyle(
      {
        mention: {
          [indicator]: {
            borderRadius: 20,
            borderWidth: 1,
            fontSize: 12,
            fontStyle: 'italic',
            fontWeight: 700,
            letterSpacing: 1,
            margin: 2,
            marginBottom: 3,
            marginLeft: 4,
            marginRight: 5,
            marginTop: 6,
            paddingHorizontal: 8,
            paddingVertical: 6,
          },
        },
      },
      [indicator]
    );
    const mention = (
      normalized.mention as Record<string, Record<string, unknown>>
    )[indicator];

    expect(mention).toMatchObject({
      borderRadius: 20,
      borderWidth: 1,
      fontSize: 12,
      fontStyle: 'italic',
      fontWeight: '700',
      letterSpacing: 1,
      margin: 2,
      marginBottom: 3,
      marginLeft: 4,
      marginRight: 5,
      marginTop: 6,
      paddingHorizontal: 8,
      paddingVertical: 6,
    });
  });
});
