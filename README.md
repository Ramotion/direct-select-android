![header](./header.png)
<img src="https://github.com/Ramotion/direct-select-android/blob/master/direct_select_preview.gif" width="560" height="420" />
# Direct Select for Android
DirectSelect Dropdown is a selection widget with an ethereal, full-screen modal popup displaying the available choices when the widget is interact with.

Inspired by [Virgil Pana](https://dribbble.com/virgilpana) [shot](https://dribbble.com/shots/3876250-DirectSelect-Dropdown-ux)

**Looking for developers for your project?**

This project is maintained by Ramotion, Inc. We specialize in the designing and coding of custom UI for Mobile Apps and Websites.

<a href="mailto:alex.a@ramotion.com?subject=Project%20inquiry%20from%20Github">
<img src="https://github.com/ramotion/gliding-collection/raw/master/contact_our_team@2x.png" width="187" height="34"></a>

The [Android mockup](https://store.ramotion.com?utm_source=gthb&utm_medium=special&utm_campaign=direct-select-android) available [here](https://store.ramotion.com?utm_source=gthb&utm_medium=special&utm_campaign=direct-select-android).

## Requirements
 - Android 4.0 IceCreamSandwich (API lvl 14) or greater
 - Your favorite IDE
## Installation
Just download the package from [here](http://central.maven.org/maven2/com/ramotion/directselect/direct-select/0.1.0/direct-select-0.1.0.aar) and add it to your project classpath, or use it as dependency in your build tool:
 - Gradle:
```groovy
'com.ramotion.directselect:direct-select:0.1.0'
```
 - SBT:
```scala
libraryDependencies += "com.ramotion.directselect" % "direct-select" % "0.1.0"
```
 - Maven:
```xml
<dependency>
	<groupId>com.ramotion.directselect</groupId>
	<artifactId>direct-select</artifactId>
	<version>0.1.0</version>
</dependency>
```
## Basic usage
Basic usage assumes a simple configuration through xml layout without writing program code, but this doesn't provide the possibility to create cells with custom content layout for your lists and pickers.
So, all you need is two elements in your layout:
```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:ds="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.ramotion.directselect.examples.basic.BasicExampleActivity">

   <com.ramotion.directselect.DSListView
        android:id="@+id/ds_picker"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@color/ds_list_bg"
        android:visibility="invisible"
        ds:cell_font_size="8sp"
        ds:data_array="@array/months"
        ds:picker_box_view="@id/picker_box"
        ds:scale_animations="true"
        ds:scale_animations_factor="1.3"
        ds:scale_animations_pivot_center="false"
        ds:selected_index="2"
        ds:selector_background="@color/ds_list_selector_bg" />

    <com.ramotion.directselect.DSDefaultPickerBox
        android:id="@+id/picker_box"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="260dp"
        android:background="@color/ds_picker_box_bg"
        android:padding="15dp" />

</FrameLayout>
```
`DSListView` represents a list with options for the picker that shows up when the user touches a `DSDefaultPickerBox` element and hides when the user removes his finger from the screen.  `DSListView` has a couple of custom attributes, some of them are required, some not, here is a list:
 - **`data_array`** is a required reference to your selector options represented as array of strings in application resources.
 - **`picker_box_view`** is a required reference to implemented picker box element, you can use default implementation provided by the library `DSDefaultPickerBox` or you can implement your own as shown in the advanced usage example in this repository.
 - **`selector_background`**  is a drawable or color responsible for a highlighting of selector position when `DSListView` is shown on the screen. Using the same background as in your PickerBox element you can achieve a pretty nice and clean effect.
 - **`scale_animations`** is a boolean that turns on/off scale animations of the selected item. Try it.
 - **`scale_animations_factor`** is a float value that defines max scale ratio for scaling animation
 - **`scale_animations_pivot_center`** is a boolean that moves pivot point of scale animations to center of your element instead of default left aligned position.
 - **`selected_index`** is an integer that represents initially selected option.

## Advanced usage
Here everything is more complicated. Let's start from creating layout files.
 1. First of all we need to implement our custom cell layout, in separate xml file, eg `advanced_example_country_cell.xml`:
 ```xml
 <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/custom_cell_root"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_centerVertical="true">

    <ImageView
        android:id="@+id/custom_cell_image"
        android:layout_width="30dp"
        android:layout_height="30dp"
        android:layout_alignParentLeft="true"
        android:layout_alignParentStart="true"
        android:layout_centerVertical="true"
        android:layout_margin="10dp"
        android:contentDescription="@string/cell_image"
        android:scaleType="centerCrop" />

    <TextView
        android:id="@+id/custom_cell_text"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerVertical="true"
        android:layout_toEndOf="@id/custom_cell_image"
        android:layout_toRightOf="@id/custom_cell_image" />

</RelativeLayout>
```
2. Now we need to create a layout for our cell in the list view, `advanced_example_country_list_item.xml`- it just wraps our newly created cell with `FrameLayout` for correct element animations and positioning inside the list view.
```xml
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:clipChildren="false"
    android:clipToPadding="false"
    android:paddingLeft="20dp"
    android:paddingRight="20dp">

    <include
        layout="@layout/advanced_example_country_cell"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />

</FrameLayout>
```
3.  Third step - creation of our custom picker box layout, a new file named `advanced_example_country_picker_box.xml` that contains our custom cell layout, and inserts an additional custom elements that must appear only in picker box, like the direction arrows in our example.
```xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@drawable/ds_bg_picker_box"
    android:paddingLeft="20dp"
    android:paddingRight="20dp">

    <include
        layout="@layout/advanced_example_country_cell"
        android:layout_height="wrap_content"
        android:layout_width="wrap_content" />

    <ImageView
        android:id="@+id/picker_box_arrows"
        android:layout_width="30dp"
        android:layout_height="30dp"
        android:layout_alignParentEnd="true"
        android:layout_alignParentRight="true"
        android:layout_centerVertical="true"
        android:layout_marginEnd="10dp"
        android:layout_marginRight="10dp"
        android:alpha="0.25"
        android:contentDescription="@string/picker_box_arrows_icon"
        android:scaleType="fitCenter"
        android:src="@drawable/ds_picker_box_arrows" />

</RelativeLayout>
```  
4. Finally, we need to write some code. First of all - we need to prepare a dataset, our cell contains two items - a title and image, so we can't use the default `data_array` attribute from the basic example. Our structure can be represented by plain old java object with two appropriate fields.
```java
public class AdvancedExampleCountryPOJO {
    private String title;
    private int icon;

    public AdvancedExampleCountryPOJO(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public static List<AdvancedExampleCountryPOJO> getExampleDataset() {
        return Arrays.asList(
                new AdvancedExampleCountryPOJO("Russian Federation", R.drawable.ds_countries_ru),
                new AdvancedExampleCountryPOJO("Canada", R.drawable.ds_countries_ca),
                new AdvancedExampleCountryPOJO("United States of America", R.drawable.ds_countries_us),
                new AdvancedExampleCountryPOJO("China", R.drawable.ds_countries_cn),
                new AdvancedExampleCountryPOJO("Brazil", R.drawable.ds_countries_br),
                new AdvancedExampleCountryPOJO("Australia", R.drawable.ds_countries_au),
                new AdvancedExampleCountryPOJO("India", R.drawable.ds_countries_in),
                new AdvancedExampleCountryPOJO("Argentina", R.drawable.ds_countries_ar),
                new AdvancedExampleCountryPOJO("Kazakhstan", R.drawable.ds_countries_kz),
                new AdvancedExampleCountryPOJO("Algeria", R.drawable.ds_countries_dz)
        );
    }

    // getters, setters, equal, hashcode, etc.
}
```
5. Now, to more complex things - we need to somehow provide our dataset to `DSListView`, for this purpose in Android we have a `android.widget.ArrayAdapter` class, so we need a custom implementation to map data from our POJO to the actual cell described earlier:
```java
public class AdvancedExampleCountryAdapter extends ArrayAdapter<AdvancedExampleCountryPOJO> {
    private List<AdvancedExampleCountryPOJO> items;
    private Context context;

    public AdvancedExampleCountryAdapter(@NonNull Context context, int resource, @NonNull List<AdvancedExampleCountryPOJO> objects) {
        super(context, resource, objects);
        this.items = objects;
        this.context = context;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AdvancedExampleCountryAdapter.ViewHolder holder;

        if (null == convertView) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert vi != null;
            convertView = vi.inflate(R.layout.advanced_example_country_list_item, parent, false);
            holder = new AdvancedExampleCountryAdapter.ViewHolder();
            holder.text = convertView.findViewById(R.id.custom_cell_text);
            holder.icon = convertView.findViewById(R.id.custom_cell_image);
            convertView.setTag(holder);
        } else {
            holder = (AdvancedExampleCountryAdapter.ViewHolder) convertView.getTag();
        }
        if (null != holder) {
            holder.text.setText(items.get(position).getTitle());
            holder.icon.setImageResource(items.get(position).getIcon());
        }
        return convertView;
    }

    private class ViewHolder {
        TextView text;
        ImageView icon;
    }
}

```
6. Almost done, but before we put it all together, there is one more thing. We almost forgot about our custom picker box to map the selected cell from the list view to the actual displayed picker. So, we need to implement simple view that inflates our layout for the picker box described earlier. To guarantee correctness, work must extend the `DSAbstractPickerBox` class and implement some abstract methods:
```java
public class AdvancedExampleCountryPickerBox extends DSAbstractPickerBox<AdvancedExampleCountryPOJO> {
    private TextView text;
    private ImageView icon;
    private View cellRoot;

    public AdvancedExampleCountryPickerBox(@NonNull Context context) {
        this(context, null);
    }

    public AdvancedExampleCountryPickerBox(@NonNull Context context,
										   @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AdvancedExampleCountryPickerBox(@NonNull Context context,
										   @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(@NonNull Context context) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert mInflater != null;
        mInflater.inflate(R.layout.advanced_example_country_picker_box, this, true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.text = findViewById(R.id.custom_cell_text);
        this.icon = findViewById(R.id.custom_cell_image);
        this.cellRoot = findViewById(R.id.custom_cell_root);
    }

    @Override
    public void onSelect(AdvancedExampleCountryPOJO selectedItem, int selectedIndex) {
        this.text.setText(selectedItem.getTitle());
        this.icon.setImageResource(selectedItem.getIcon());
    }

    @Override
    public View getCellRoot() {
        return this.cellRoot;
    }
}
```
7. Finally - put all parts together in the main activity layout and write some code in `MainActivity`:
```java
public class AdvancedExampleActivity extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advanced_example_activity);

		// Prepare dataset
        List<AdvancedExampleCountryPOJO> exampleDataSet = AdvancedExampleCountryPOJO.getExampleDataset();

		// Create adapter with our dataset
        ArrayAdapter<AdvancedExampleCountryPOJO> adapter = new AdvancedExampleCountryAdapter(
                this, R.layout.advanced_example_country_list_item, exampleDataSet);

		// Set adapter to our DSListView
        DSListView<AdvancedExampleCountryPOJO> pickerView = findViewById(R.id.ds_county_list);
        pickerView.setAdapter(adapter);

    }
}
```
That's all. There are still some unmentioned features, like the possibility to change the size and position of the list view and the possibility to change default cell text appearance through `cell-font-size` settings or through applying your custom style.
Full examples with some comments and explanations can be found in this repository. Feel free to report bugs and ask questions.

<br>

This library is a part of a <a href="https://github.com/Ramotion/android-ui-animation-components-and-libraries"><b>selection of our best UI open-source projects.</b></a>

## Licence

Folding cell is released under the MIT license.
See [LICENSE](./LICENSE.md) for details.

# Get the Showroom App for Android to give it a try
Try our UI components in our mobil app. Contact us if interested.

<a href="https://play.google.com/store/apps/details?id=com.ramotion.showroom" >
<img src="https://raw.githubusercontent.com/Ramotion/react-native-circle-menu/master/google_play@2x.png" width="104" height="34"></a>
<a href="mailto:alex.a@ramotion.com?subject=Project%20inquiry%20from%20Github">
<img src="https://github.com/ramotion/gliding-collection/raw/master/contact_our_team@2x.png" width="187" height="34"></a>

Follow us for the latest updates:

<a href="https://goo.gl/rPFpid" >
<img src="https://i.imgur.com/ziSqeSo.png/" width="156" height="28"></a>
