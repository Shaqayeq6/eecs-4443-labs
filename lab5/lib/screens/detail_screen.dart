import 'package:flutter/material.dart';
import '../models/item.dart';

class DetailScreen extends StatelessWidget {
  final Item item;

  const DetailScreen({super.key, required this.item});

  String get safeTitle =>
      item.title.trim().isEmpty ? "Untitled Item" : item.title.trim();
  String get safeDesc => item.description.trim().isEmpty
      ? "No description available."
      : item.description.trim();

  @override
  Widget build(BuildContext context) {
    final isLandscape =
        MediaQuery.of(context).orientation == Orientation.landscape;

    return Scaffold(
      appBar: AppBar(title: Text(safeTitle)),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16),
        child: isLandscape
            ? Row(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Expanded(child: _imageBlock()),
            const SizedBox(width: 16),
            Expanded(child: _textBlock(context)),
          ],
        )
            : Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            _imageBlock(),
            const SizedBox(height: 14),
            _textBlock(context),
          ],
        ),
      ),
    );
  }

  Widget _imageBlock() {
    return ClipRRect(
      borderRadius: BorderRadius.circular(18),
      child: AspectRatio(
        aspectRatio: 16 / 10,
        child: Image.network(
          item.imageUrl,
          fit: BoxFit.cover,
          errorBuilder: (_, __, ___) {
            return Container(
              alignment: Alignment.center,
              color: Colors.grey.shade200,
              child: const Icon(Icons.broken_image_outlined, size: 42),
            );
          },
        ),
      ),
    );
  }

  Widget _textBlock(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          safeTitle,
          style: Theme.of(context).textTheme.headlineSmall?.copyWith(
            fontWeight: FontWeight.w900,
          ),
        ),
        const SizedBox(height: 10),
        Text(
          safeDesc,
          style: Theme.of(context).textTheme.bodyLarge?.copyWith(
            height: 1.45,
            color: Colors.grey.shade800,
          ),
        ),
      ],
    );
  }
}
