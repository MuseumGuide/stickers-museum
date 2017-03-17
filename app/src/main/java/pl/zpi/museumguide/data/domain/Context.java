package pl.zpi.museumguide.data.domain;

/**
 * Context::period defines a period during which the given context is suitable
 */
public class Context {

	private String name;
	private String text;
	private Period period;
    private ContextType type;

	public Context() {
	}

	public Context(String name, String text, Period period, ContextType type) {
		this.name = name;
		this.text = text;
		this.period = period;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public ContextType getType() {
		return type;
	}

	public void setType(ContextType type) {
		this.type = type;
	}
}