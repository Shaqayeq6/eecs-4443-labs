import 'package:flutter/material.dart';
import 'package:flutter/material.dart';
import '../data/sample_items.dart';
import '../models/item.dart';
import '../widgets/item_card.dart';
import 'detail_screen.dart';

class ListScreen extends StatelessWidget {
  const ListScreen({super.key});

  void _openDetail(BuildContext context, Item item) {
    Navigator.push(
      context,
      MaterialPageRoute(builder: (_) => DetailScreen(item: item)),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: const [
            Text(
              "Lab 5 – Dynamic List",
              style: TextStyle(fontWeight: FontWeight.w700),
            ),
            SizedBox(height: 2),
            Text(
              "Created by: Shaqayeq Salimy",
              style: TextStyle(
                fontSize: 12,
                fontWeight: FontWeight.w400,
              ),
            ),
          ],
        ),
      ),
      body: ListView.builder(
        itemCount: sampleItems.length,
        itemBuilder: (context, index) {
          final item = sampleItems[index];
          return ItemCard(
            item: item,
            onTap: () => _openDetail(context, item),
          );
        },
      ),
    );
  }
}
