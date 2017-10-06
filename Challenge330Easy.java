import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * /r/dailyprogrammer challenge #330 - easy
 *
 * https://www.reddit.com/r/dailyprogrammer/comments/6y19v2/20170904_challenge_330_easy_surround_the_circles/
 */
public class Challenge330Easy {
	public static void main(String[] args) {
		Rectangle rectangle;
		List<String> lines = readFile(args[0]);
		boolean is_bonus = lines.get(0).split(",").length < 3;
		if (is_bonus) {
			String[] unit_vector = lines.get(0).split(",");
			double vx = Double.valueOf(unit_vector[0]);
			double vy = Double.valueOf(unit_vector[1]);
			double norm = Math.sqrt(vx*vx + vy*vy);
			vx /= norm;
			vy /= norm;
			rectangle = new Rectangle(vx, vy);
			for (int i = 1; i < lines.size(); i++) {
				rectangle.addCircle(new Circle(lines.get(i), vx, vy));
			}
		} else {
			rectangle = new Rectangle();
			for (int i = 0; i < lines.size(); i++) {
				rectangle.addCircle(new Circle(lines.get(i)));
			}
		}
		rectangle.printCoordinates();
	}

	public static List<String> readFile(String file_name) {
		List<String> lines = new ArrayList<String>();
		try {
			lines.addAll(Files.readAllLines(Paths.get(file_name), Charset.defaultCharset()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lines;
	}

	private static class Circle {
		public double x;
		public double y;
		public double r;
		Double vx;
		Double vy;
		public Circle(String line) {
			this(line, null, null);
		}
		public Circle(String line, Double vx, Double vy) {
			String[] parameters = line.split(",");
			this.x = Double.valueOf(parameters[0]);
			this.y = Double.valueOf(parameters[1]);
			this.r = Double.valueOf(parameters[2]);
			this.vx = vx;
			this.vy = vy;
		}
		public double getXMin() {
			double rx;
			if (null == this.vx) {
				rx = this.x;
			} else {
				rx = this.x*this.vx - this.y*this.vy;
			}
			return rx - this.r;
		}
		public double getXMax() {
			double rx;
			if (null == this.vx) {
				rx = this.x;
			} else {
				rx = this.x*this.vx - this.y*this.vy;
			}
			return rx + this.r;
		}
		public double getYMin() {
			double ry;
			if (null == this.vy) {
				ry = this.x;
			} else {
				ry = this.x*this.vx + this.y*this.vy;
			}
			return ry - this.r;
		}
		public double getYMax() {
			double ry;
			if (null == this.vy) {
				ry = this.x;
			} else {
				ry = this.x*this.vx + this.y*this.vy;
			}
			return ry + this.r;
		}
	}

	private static class Rectangle {
		public Double x_min;
		public Double x_max;
		public Double y_min;
		public Double y_max;
		public List<Circle> circles;
		public Double vx;
		public Double vy;
		public Rectangle() {
			this(null, null);
		}
		public Rectangle(Double vx, Double vy) {
			this.x_min = null;
			this.x_max = null;
			this.y_min = null;
			this.y_max = null;
			this.circles = new ArrayList<Circle>();
			this.vx = vx;
			this.vy = vy;
		}
		public void addCircle(Circle circle) {
			if (null == this.x_min || this.x_min > circle.getXMin()) {
				this.x_min = circle.getXMin();
			}
			if (null == this.x_max || this.x_max < circle.getXMax()) {
				this.x_max = circle.getXMax();
			}
			if (null == this.y_min || this.y_min > circle.getYMin()) {
				this.y_min = circle.getYMin();
			}
			if (null == this.y_max || this.y_max < circle.getYMax()) {
				this.y_max = circle.getYMax();
			}
			this.circles.add(circle);
		}
		public void printCoordinates() {
			double x0 = this.x_min;
			double y0 = this.y_min;
			double x1 = this.x_min;
			double y1 = this.y_max;
			double x2 = this.x_max;
			double y2 = this.y_max;
			double x3 = this.x_max;
			double y3 = this.y_min;
			if (null != this.vx) {
				x0 = this.x_min*this.vx + this.y_min*this.vy;
				y0 = this.y_min*this.vx - this.x_min*this.vy;
				x1 = this.x_min*this.vx + this.y_max*this.vy;
				y1 = this.y_max*this.vx - this.x_min*this.vy;
				x2 = this.x_max*this.vx + this.y_max*this.vy;
				y2 = this.y_max*this.vx - this.x_max*this.vy;
				x3 = this.x_max*this.vx + this.y_min*this.vy;
				y3 = this.y_min*this.vx - this.x_max*this.vy;
			}
			System.out.printf("(%.3f, %.3f), (%.3f, %.3f), (%.3f, %.3f), (%.3f, %.3f)\n", x0, y0, x1, y1, x2, y2, x3, y3);
		}
	}
}
